import os
import shutil
import socket
import subprocess
import tempfile
import time
import traceback
from os import walk

from django.conf import settings
from django.core.management.base import BaseCommand
from django.utils import timezone

from website.models import Dataset, Project, ProjectSnapshot


'''Run repository cloner

Usage: manage.py runcloner
Runs a poller in the background to grab project URLs and clone them
'''
class Command(BaseCommand):
    help = 'Runs a poller in the background to grab project URLs and clone them'
    POLL_INTERVAL = 10

    def add_arguments(self, parser):
        parser.add_argument('url', nargs='*', help='Clone specific URL(s)', type=str)
        parser.add_argument('--dry-run', help='Perform a dry-run (don\'t change the database or disk)', action='store_true')
        parser.add_argument('--no-poll', help='Perform one round of processing instead of polling', action='store_true')

    def handle(self, *args, **options):
        self.dry_run = options['dry_run']
        self.no_poll = options['no_poll']
        self.verbosity = options['verbosity']

        if not os.path.exists(getattr(settings, 'REPO_PATH')):
            self.stderr.write('repository storage path does not exist: ' + getattr(settings, 'REPO_PATH'))
            return

        for url in options['url']:
            try:
                project = Project.objects.get(url=url)
            except Project.DoesNotExist:
                if not self.dry_run:
                    project = Project.objects.create(dataset=Dataset.objects.first(), url=url)
                else:
                    project = Project(url=url)

            try:
                snapshot = ProjectSnapshot.objects.get(project=project)
            except ProjectSnapshot.DoesNotExist:
                if not self.dry_run:
                    snapshot = ProjectSnapshot.objects.create(project=project)
                else:
                    snapshot = ProjectSnapshot(project=project)

            try:
                self.process_snapshot(snapshot)
            except:
                self.stdout.write('error processing: ' + project.url)
                traceback.print_exc()

        if len(options['url']) == 0:
            while True:
                snapshot = ProjectSnapshot.objects.filter(host=None).first()
                if snapshot:
                    try:
                        self.process_snapshot(snapshot)
                    except:
                        self.stderr.write('problem processing ProjectSnapshot: ' + snapshot.project.url)
                        traceback.print_exc()

                if self.no_poll:
                    break
                if not snapshot:
                    time.sleep(self.POLL_INTERVAL)

    def process_snapshot(self, snapshot):
        self.stdout.write('processing ProjectSnapshot: ' + snapshot.project.url)
        if not self.dry_run:
            snapshot.host = socket.gethostname()
            snapshot.save()

        host = snapshot.project.url[snapshot.project.url.index('://') + 3:]
        project_name = host[host.index('/') + 1:]
        project_base = project_name[0:project_name.index('/')] if '/' in project_name else project_name
        project_parent = project_name[0:project_name.rindex('/')] if '/' in project_name else ''
        host = host[:host.index('/')]

        repo_root = os.path.join(getattr(settings, 'REPO_PATH'), host)
        path = os.path.join(repo_root, project_name)
        tmp = tempfile.gettempdir()
        src_dir = os.path.join(tmp, project_name)
        git_dir = os.path.join(src_dir, '.git')

        if self.verbosity >= 3:
            self.stdout.write('    -> root:    ' + repo_root)
            self.stdout.write('    -> name:    ' + project_name)
            self.stdout.write('    -> base:    ' + project_base)
            self.stdout.write('    -> path:    ' + path)
            self.stdout.write('    -> tmp:     ' + tmp)
            self.stdout.write('    -> src_dir: ' + src_dir)
            self.stdout.write('    -> git_dir: ' + git_dir)

        if os.path.exists(path):
            self.stdout.write('    -> SKIPPING: already exists: ' + project_name)
            if not self.dry_run:
                snapshot.path = os.path.join(host, project_name)
                snapshot.datetime_processed = timezone.now()
                snapshot.save()
            return

        try:
            self.clone_repo(host, src_dir, project_name, git_dir, repo_root, project_parent, snapshot)
        finally:
            # cleanup temp files
            if os.path.exists(os.path.join(tmp, project_base)):
                shutil.rmtree(os.path.join(tmp, project_base))

            if not self.dry_run:
                snapshot.datetime_processed = timezone.now()
                snapshot.save()

    def clone_repo(self, host, src_dir, project_name, git_dir, repo_root, project_parent, snapshot):
        # clone and filter the repo
        os.makedirs(src_dir, 0o755, True)
        self.run_command(['git', 'init'], src_dir)

        self.run_command(['git', 'config', '--local', 'core.sparseCheckout', 'true'], src_dir)
        with open(os.path.join(src_dir, '.git/info/sparse-checkout'), 'w') as sparseFile:
            sparseFile.writelines('**/*.java\n')

        # get all remote objects
        self.run_command(['git', 'remote', 'add', 'origin', 'https://foo:bar@' + host + '/' + project_name], src_dir)
        p = self.run_command(['git', 'fetch'], src_dir)

        if p.returncode == 0:
            # filter out unwanted objects
            self.run_command(['git', 'filter-repo', '--path-regex', '^.*/*\\.java$'], src_dir)
            self.run_command(['git', 'remote', 'add', 'origin', 'https://foo:bar@' + host + '/' + project_name], src_dir)

            # unpack object files
            self.unpack_git(git_dir)

            # checkout working tree
            # similar to: git remote show origin | grep "HEAD branch" | cut -d ":" -f 2
            masterrefproc = subprocess.Popen(['git', 'remote', 'show', 'origin'], cwd=src_dir, stdout=subprocess.PIPE, stderr=None)
            parts = filter(lambda x: 'HEAD branch' in x, masterrefproc.stdout.read().decode().strip().split('\n'))
            masterref = list(parts)[0].split(':')[1].strip()
            p = self.run_command(['git', 'checkout', masterref], src_dir)

            if not self.dry_run:
                if p.returncode == 0:
                    if not os.path.exists(repo_root):
                        self.stdout.write('root does not exist, creating: ' + repo_root)
                        os.makedirs(repo_root, 0o755, True)

                    self.update_metrics(snapshot, src_dir)

                    # move repo to final location
                    parent_dir = os.path.join(repo_root, project_parent)
                    if self.verbosity >= 2:
                        self.stdout.write('moving ' + src_dir + ' -> ' + parent_dir)
                    if not os.path.exists(parent_dir):
                        os.makedirs(parent_dir, 0o755, True)
                    shutil.move(src_dir, parent_dir)

                    snapshot.path = os.path.join(host, project_name)
                else:
                    if self.verbosity >= 2:
                        self.stdout.write('failed to clone ' + project_name)

    def snapshot_project(self, snapshot, path):
        # zfs snapshot paclab/"$project_name"@"$timestamp"
        # TODO ZFS snapshot
        pass

    """unpack all Git object files"""
    def unpack_git(self, git_dir):
        if os.path.exists(os.path.join(git_dir, 'objects/pack')):
            packdir = os.path.join(git_dir, 'pack')
            shutil.move(os.path.join(git_dir, 'objects/pack'), packdir)

            for (root, dirs, files) in walk(packdir):
                for f in files:
                    if f.endswith('.pack'):
                        with open(os.path.join(root, f), 'rb') as packfile:
                            unpack = subprocess.Popen(['git', 'unpack-objects'], cwd=git_dir, stdin=subprocess.PIPE, stdout=None, stderr=None)
                            while True:
                                b = packfile.read(64 * 1024)
                                if not b:
                                    break
                                unpack.stdin.write(b)

            shutil.rmtree(packdir)

    def update_metrics(self, project, path):
        # update number of commits
        p = subprocess.Popen(['git', 'rev-list', '--count', 'HEAD'], cwd=path, stdout=subprocess.PIPE, stderr=None)
        project.commits = int(p.stdout.read().decode().strip())
        if self.verbosity >= 2:
            self.stdout.write(path + ', commits =' + str(project.commits))

        # update number of committers
        p = subprocess.Popen(['git', 'shortlog', '-sne'], cwd=path, stdout=subprocess.PIPE, stderr=None)
        project.committers = p.stdout.read().decode().count('\n')
        if self.verbosity >= 2:
            self.stdout.write(path + ', committers =' + str(project.committers))

        # update src file counts
        # similar to: find . -name '*.java' | wc -l
        p = subprocess.Popen(['find', '.', '-name', '*.java'], cwd=path, stdout=subprocess.PIPE, stderr=None)
        project.src_files = p.stdout.read().decode().count('\n')
        if self.verbosity >= 2:
            self.stdout.write(path + ', src_files =' + str(project.src_files))

    def run_command(self, args, cwd):
        p = subprocess.Popen(args,
                             cwd=cwd,
                             stdout=subprocess.PIPE if self.verbosity < 3 else None,
                             stderr=subprocess.PIPE if self.verbosity < 3 else None)
        p.wait()
        return p
