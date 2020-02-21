import time
import traceback
import importlib
import multiprocessing

from django.core.management.base import BaseCommand
from website.models import ProjectSelector
from django.conf import settings

from website.choices import *

'''Run Filters Poller

Usage: manage.py runfilters
Runs a poller in the background to grab unprocessed project selections from database
'''
class Command(BaseCommand):
    help = 'Runs a poller in the background to grab unprocessed project selections from database'
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
                self.process_selection(ProjectSelector.objects.get(slug=slug))
            except ProjectSelector.DoesNotExist:
                self.stdout.write('invalid slug: ' + slug)
            except:
                self.stdout.write('error processing: ' + slug)
                traceback.print_exc()

        if len(options['slug']) == 0:
            while True:
                selector = ProjectSelector.objects.filter(status=READY)[0]
                if selector:
                    try:
                        self.process_selection(selector)
                    except:
                        self.stdout.write('error processing: ' + selector.slug)
                        traceback.print_exc()

                if self.no_poll:
                    break
                time.sleep(self.POLL_INTERVAL)

    def process_selection(self, selector):
        self.stdout.write('processing ProjectSelection: ' + selector.slug)
        if not self.dry_run:
            selector.status = ONGOING
            selector.save()

        filters = selector.filterdetail_set.exclude(pfilter__enabled=False)
        backends = set()
        for pfilter in filters:
            associated_backend = str(pfilter.pfilter.associated_backend)
            backends.add((associated_backend, pfilter.pfilter.associated_backend))

        for (backend, backend_id) in backends:
            modname = backend + '_backend.filterrunner'
            backend = importlib.import_module(modname)
            filterrunner = backend.FilterRunner(selector, backend_id, self.dry_run, self.verbosity)
            if self.verbosity >= 2:
                self.stdout.write('    -> calling backend: ' + modname)
            #process = multiprocessing.Process(target=filterrunner.run, args=())
            if self.debug:
                filterrunner.debug()
            else:
                filterrunner.run()
