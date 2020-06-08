import importlib
import multiprocessing
import time
import traceback

from django.conf import settings
from django.core.management.base import BaseCommand

from website.choices import *
from website.models import ProjectSelector, ProjectSnapshot, Selection
from django.utils import timezone
import socket


'''Run Filter Poller

Usage: manage.py runfilters
Runs a poller in the background to run filters on cloned repos
'''
class Command(BaseCommand):
    help = 'Runs a poller in the background to run filters on cloned repos'
    POLL_INTERVAL = 3

    def add_arguments(self, parser):
        parser.add_argument('slug', nargs='*', help='Specific project selection slug(s) to process', type=str)
        parser.add_argument('--debug', help='Debug filtering specific slug(s) (implies --dry-run --no-poll -v 3)', action='store_true')
        parser.add_argument('--dry-run', help='Perform a dry-run (don\'t change the database)', action='store_true')
        parser.add_argument('--no-poll', help='Perform one round of processing instead of polling', action='store_true')

    def handle(self, *args, **options):
        self.debug = options['debug']
        self.dry_run = options['dry_run'] or self.debug
        self.no_poll = options['no_poll'] or self.debug
        self.verbosity = 3 if self.debug else options['verbosity']

        if self.debug and len(options['slug']) == 0:
            self.stderr.write('--debug requires providing a slug')
            exit(-1)

        for slug in options['slug']:
            try:
                self.process_selector(ProjectSelector.objects.get(slug=slug))
            except ProjectSelector.DoesNotExist:
                self.stdout.write('invalid slug: ' + slug)
            except:
                self.stdout.write('error processing: ' + slug)
                traceback.print_exc()

        if len(options['slug']) == 0:
            while True:
                selector = ProjectSelector.objects.filter(enabled=True, status=READY).first()
                if selector:
                    try:
                        self.process_selector(selector)
                    except:
                        self.stdout.write('error processing: ' + selector.slug)
                        traceback.print_exc()

                if self.no_poll:
                    break
                time.sleep(self.POLL_INTERVAL)

    def process_selector(self, selector):
        self.stdout.write('processing ProjectSelection: ' + selector.slug)
        if not self.dry_run:
            selector.status = ONGOING
            selector.save()

        for snapshot in Selection.objects.filter(project=selector,host=socket.gethostname()).exclude(path__isnull=True):
            if self.test_repo(snapshot.path):
                self.retained_selection(snapshot)
            else:
                self.filtered_selection(snapshot)

        if not selector.filterdetail_set.exclude(status=PROCESSED).exists():
            self.selector.status = PROCESSED
            self.selector.fin_process = timezone.now()
            self.selector.save()

    def test_repo(self, path):
        return True

    def retained_selection(self, selection):
        if self.verbosity >= 3:
            print("-> retained project: " + selection.snapshot.project.url)
        if self.dry_run:
            return

        selection.retained = True
        selection.save()

    def filtered_selection(self, selection):
        if self.verbosity >= 3:
            print("-> filtered project: " + selection.snapshot.project.url)
        if self.dry_run:
            return

        selection.retained = False
        selection.save()
