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

        host = project.url[project.url.index("://") + 3:]
        project_name = host[host.index("/") + 1:]
        host = host[:host.index("/")]

        tmp = '/tmp/' + project_name[:project_name.rindex('/')]
        repo_root = config('REPO_PATH')
        if repo_root[-1:] != '/':
            repo_root += '/'
        repo_root += host
        path = repo_root + '/' + project_name

        if self.verbosity >= 3:
            self.stdout.write('    -> root: ' + repo_root)
            self.stdout.write('    -> tmp: ' + tmp)
            self.stdout.write('    -> path: ' + path)

        if os.path.exists(path):
            self.stdout.write('    -> SKIPPING: already exists: ' + project_name)
            if not self.dry_run:
                project.path = path
                project.datetime_processed = timezone.now()
                project.save()
            return

        # clone
        p = subprocess.Popen(['git', 'clone', '--depth', '1', 'https://foo:bar@' + host + '/' + project_name, project_name], cwd='/tmp/', stdout=subprocess.PIPE if self.verbosity < 2 else None, stderr=subprocess.PIPE if self.verbosity < 2 else None)
        if self.verbosity >= 2:
            self.stdout.write('    -> process id: ' + str(p.pid))
        p.wait()

        if not self.dry_run:
            if p.returncode == 0:
                if not os.path.exists(repo_root):
                    self.stdout.write('root does not exist, creating: ' + repo_root)
                    os.mkdir(repo_root, 0o755)
                if self.verbosity >= 2:
                    self.stdout.write('moving ' + tmp + ' -> ' + repo_root)
                # move repo to final location
                shutil.move(tmp, repo_root)

                project.path = host + '/' + project_name
                project.datetime_processed = timezone.now()
                project.save()
            else:
                project.datetime_processed = timezone.now()
                project.save()

        # cleanup temp files
        if os.path.exists(tmp):
            shutil.rmtree(tmp)
