import time
import importlib
import pytz
import subprocess
import socket
import shutil
import os

from django.core.management.base import BaseCommand
from django.utils import timezone
from website.models import Project
from django.conf import settings
from decouple import config

from website.choices import *

'''Run repository cloner

Usage: manage.py runcloner
Runs a poller in the background to grab project URLs and clone them
'''
class Command(BaseCommand):
    help = 'Runs a poller in the background to grab project URLs and clone them'
    
    def add_arguments(self, parser):
        parser.add_argument('--dry-run', help='Perform a dry-run (don\'t change the database or disk)', action='store_true')
        parser.add_argument('--no-poll', help='Perform one round of processing instead of polling', action='store_true')

    def handle(self, *args, **options):
        self.dry_run = options['dry_run']
        self.no_poll = options['no_poll']
        self.verbosity = options['verbosity']

        if not os.path.exists(config('REPO_PATH')):
            self.stderr.write('repository storage path does not exist: ' + config('REPO_PATH'))
            return

        while True:
            projects = Project.objects.filter(host=None)[:10]
            for project in projects:
                try:
                    self.process_project(project)
                except:
                    self.stderr.write('problem processing Project: ' + project.url)

            if self.no_poll:
                break
            time.sleep(3)

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

        repo_root = os.path.join(config('REPO_PATH'), host)
        path = os.path.join(repo_root, project_name)
        tmp = '/tmp/'

        if self.verbosity >= 3:
            self.stdout.write('    -> root: ' + repo_root)
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
        p = subprocess.Popen(['git', 'clone', '--depth', '1', 'https://foo:bar@' + host + '/' + project_name, project_name], cwd=tmp, stdout=subprocess.PIPE if self.verbosity < 3 else None, stderr=subprocess.PIPE if self.verbosity < 3 else None)
        if self.verbosity >= 2:
            self.stdout.write('    -> process id: ' + str(p.pid))
        p.wait()

        src_dir = os.path.join(tmp, project_name)

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
            else:
                if self.verbosity >= 2:
                    self.stdout.write('failed to clone ' + project_name)

            project.datetime_processed = timezone.now()
            project.save()

        # cleanup temp files
        if os.path.exists(src_dir):
            shutil.rmtree(src_dir)
