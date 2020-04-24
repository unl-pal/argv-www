import importlib
import os
import shutil
import socket
import subprocess
import tempfile
import time

import pytz
from django.conf import settings
from django.core.management.base import BaseCommand
from django.utils import timezone

from website.choices import *
from website.models import Dataset, Project


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
                self.process_project(project)
            except:
                self.stdout.write('error processing: ' + project.url)

        if len(options['url']) == 0:
            while True:
                project = Project.objects.filter(host=None).first()
                if project:
                    try:
                        self.process_project(project)
                    except:
                        self.stderr.write('problem processing Project: ' + project.url)

                if self.no_poll:
                    break
                if not project:
                    time.sleep(self.POLL_INTERVAL)


    def process_project(self, project):
        self.stdout.write('processing Project: ' + project.url)
        if not self.dry_run:
            project.host = socket.gethostname()
            project.save()

        host = project.url[project.url.index('://') + 3:]
        project_name = host[host.index('/') + 1:]
        project_base = project_name[0:project_name.index('/')] if '/' in project_name else project_name
        project_parent = project_name[0:project_name.rindex('/')] if '/' in project_name else ''
        host = host[:host.index('/')]

        repo_root = os.path.join(getattr(settings, 'REPO_PATH'), host)
        path = os.path.join(repo_root, project_name)
        tmp = tempfile.gettempdir()

        if self.verbosity >= 3:
            self.stdout.write('    -> root: ' + repo_root)
            self.stdout.write('    -> tmp:  ' + tmp)
            self.stdout.write('    -> path: ' + path)
            self.stdout.write('    -> name: ' + project_name)
            self.stdout.write('    -> base: ' + project_base)

        if os.path.exists(path):
            self.stdout.write('    -> SKIPPING: already exists: ' + project_name)
            if not self.dry_run:
                project.path = os.path.join(host, project_name)
                project.datetime_processed = timezone.now()
                project.save()
            return

        # clone
        src_dir = os.path.join(tmp, project_name)

        p = subprocess.Popen(['/bin/sh', 'create.sh', 'https://foo:bar@' + host + '/' + project_name],
                             cwd=src_dir,
                             stdout=subprocess.PIPE if self.verbosity < 3 else None,
                             stderr=subprocess.PIPE if self.verbosity < 3 else None)
        if self.verbosity >= 2:
            self.stdout.write('    -> process id: ' + str(p.pid))
        p.wait()

        if not self.dry_run:
            if p.returncode == 0:
                if not os.path.exists(repo_root):
                    self.stdout.write('root does not exist, creating: ' + repo_root)
                    os.makedirs(repo_root, 0o755, True)

                # move repo to final location
                parent_dir = os.path.join(repo_root, project_parent)
                if self.verbosity >= 2:
                    self.stdout.write('moving ' + src_dir + ' -> ' + parent_dir)
                if not os.path.exists(parent_dir):
                    os.makedirs(parent_dir, 0o755, True)
                shutil.move(src_dir, parent_dir)

                project.path = os.path.join(host, project_name)

                self.snapshot_project(project)
            else:
                if self.verbosity >= 2:
                    self.stdout.write('failed to clone ' + project_name)

            project.datetime_processed = timezone.now()
            project.save()

        # cleanup temp files
        if os.path.exists(os.path.join(tmp, project_base)):
            shutil.rmtree(os.path.join(tmp, project_base))


    def snapshot_project(self, project):
        # TODO ZFS snapshot
        # zfs snapshot paclab/"$project_name"@"$timestamp"
        pass
