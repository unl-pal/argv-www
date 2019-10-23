import time
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
    
    def add_arguments(self, parser):
        parser.add_argument('slug', nargs='*', help='Specific project selection slug(s) to process', type=str)
        parser.add_argument('--dry-run', help='Perform a dry-run (don\'t change the database)', action='store_true')
        parser.add_argument('--no-poll', help='Perform one round of processing instead of polling', action='store_true')

    def handle(self, *args, **options):
        self.dry_run = options['dry_run']
        self.no_poll = options['no_poll']
        self.verbosity = options['verbosity']

        for slug in options['slug']:
            try:
                self.process_selection(ProjectSelector.objects.get(slug=slug,status=READY))
            except:
                self.stdout.write('error processing: ' + slug)

        if len(options['slug']) == 0:
            while True:
                selectors = ProjectSelector.objects.filter(status=READY)
                for selector in selectors:
                    self.process_selection(selector)

                if self.no_poll:
                    break
                time.sleep(3)

    def process_selection(self, selector):
        self.stdout.write('processing ProjectSelection: ' + selector.slug)
        if not self.dry_run:
            selector.status = ONGOING
            selector.save()

        filters = selector.filterdetail_set.exclude(enabled=False)
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
            filterrunner.run()
