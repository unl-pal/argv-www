import shutil
import time
from os import listdir, makedirs
from os.path import isdir, join

from django.conf import settings
from django.core.management.base import BaseCommand

from website.models import Project, TransformedProject


'''Run backend disk balancer

Usage: manage.py balancer
Runs a balancer on the head node to balance data on the slaves
'''
class Command(BaseCommand):
    help = 'Runs a balancer on the head node to balance data on the slaves'
    POLL_INTERVAL = 600
    BALANCE_THRESHOLD = 20

    def add_arguments(self, parser):
        parser.add_argument('--dry-run', help='Perform a dry-run (don\'t change the disk/database)', action='store_true')
        parser.add_argument('--no-poll', help='Perform one round of processing instead of polling', action='store_true')
        parser.add_argument('--threshold', help='Allowable threshold between devices (in percent, default=' + str(self.BALANCE_THRESHOLD) + ')', action='store')

    def handle(self, *args, **options):
        self.dry_run = options['dry_run']
        self.no_poll = options['no_poll']
        self.verbosity = options['verbosity']
        if 'threshold' in options and options['threshold']:
            self.BALANCE_THRESHOLD = int(options['threshold'])

        self.repo_path = getattr(settings, 'REPO_PATH')
        self.transformed_path = getattr(settings, 'TRANSFORMED_PATH')

        self.update_hosts()
        self.update_usages()

        while True:
            host = self.get_src_node()
            if host:
                try:
                    self.move_project(Project.objects.filter(host=host).exclude(path__isnull=True).first(), self.get_dest_node())
                except:
                    self.stderr.write('failed moving project')

            if self.no_poll:
                break

            if host:
                time.sleep(self.POLL_INTERVAL)

    def update_hosts(self):
        self.hosts = [d for d in listdir(self.transformed_path) if isdir(join(self.transformed_path, d))]

    def update_usages(self):
        usage = {}
        for host in self.hosts:
            u = shutil.disk_usage(join(self.repo_path, host))
            usage[host] = 100 * u.free / u.total
        self.usage = sorted(usage.items(), key=lambda x: x[1])

    def get_src_node(self):
        if self.usage[-1][1] - self.usage[0][1] < self.BALANCE_THRESHOLD:
            return None
        return self.usage[0][0]

    def get_dest_node(self):
        return self.usage[-1][0]

    def move_project(self, project, dest):
        if not project:
            return
        if project.host == dest:
            return

        self.stdout.write('balancing project: ' + project.url)

        if self.verbosity >= 2:
            self.stdout.write('    ' + project.host + ' -> ' + dest)
        if not self.dry_run:
            srcpath = join(self.repo_path, project.host, project.path)
            if not isdir(srcpath):
                self.stdout.write('    -> project no longer exists, marking for clone')
                project.host = None
                project.path = None
                project.save()
            else:
                destpath = join(self.repo_path, dest, project.path)
                if self.verbosity >= 3:
                    self.stdout.write('    ' + srcpath + ' -> ' + destpath)
                makedirs(destpath, 0o755, True)
                shutil.rmtree(destpath, True)
                shutil.copytree(srcpath, destpath, symlinks=True, ignore_dangling_symlinks=True)
                project.host = dest
                project.save()
                shutil.rmtree(srcpath)

        for t in TransformedProject.objects.filter(project=project):
            self.move_transform(t, dest)

        self.update_usages()

    def move_transform(self, transform, dest):
        if self.verbosity >= 2:
            self.stdout.write('        -> transform (' + str(transform.pk) + ') ' + transform.host + ' -> ' + dest)
        if not self.dry_run:
            srcpath = join(self.transformed_path, transform.host, transform.path)
            if not isdir(srcpath):
                self.stdout.write('        -> transform no longer exists - removing')
                transform.delete()
            else:
                destpath = join(self.transformed_path, dest, transform.path)
                if self.verbosity >= 3:
                    self.stdout.write('    ' + srcpath + ' -> ' + destpath)
                makedirs(destpath, 0o755, True)
                shutil.rmtree(destpath, True)
                shutil.copytree(srcpath, destpath, symlinks=True, ignore_dangling_symlinks=True)
                transform.host = dest
                transform.save()
                shutil.rmtree(srcpath)

        for t in TransformedProject.objects.filter(parent=transform):
            self.move_transform(t, dest)
