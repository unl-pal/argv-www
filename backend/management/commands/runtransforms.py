import time
import importlib
import multiprocessing

from django.core.management.base import BaseCommand
from website.models import ProjectTransformer
from django.conf import settings

from website.choices import *

'''Run Transforms Poller

Usage: manage.py runtransforms
Runs a poller in the background to grab unprocessed project transforms from database
'''
class Command(BaseCommand):
    help = 'Runs a poller in the background to grab unprocessed project transforms from database'
    
    def add_arguments(self, parser):
        parser.add_argument('slug', nargs='*', help='Specific project transformer slug(s) to process', type=str)
        parser.add_argument('--dry-run', help='Perform a dry-run (don\'t change the database)', action='store_true')
        parser.add_argument('--no-poll', help='Perform one round of processing instead of polling', action='store_true')

    def handle(self, *args, **options):
        self.dry_run = options['dry_run']
        self.no_poll = options['no_poll']
        self.verbosity = options['verbosity']

        for slug in options['slug']:
            try:
                self.process_transform(ProjectTransformer.objects.get(slug=slug,processed=READY))
            except:
                self.stdout.write('error processing: ' + slug)

        if len(options['slug']) == 0:
            while True:
                transforms = ProjectTransformer.objects.filter(processed=READY)
                for transform in transform:
                    self.process_transform(transform)

                if self.no_poll:
                    break
                time.sleep(3)

    def process_transform(self, transform):
        self.stdout.write('processing ProjectTransformer: ' + transform.slug)
        if not self.dry_run:
            transform.processed = ONGOING
            transform.save()

        transforms = transform.transforms_set.all()
        backends = set()
        for t in transforms:
            associated_backend = str(t.associated_backend)
            backends.add((associated_backend, t.associated_backend))

        for (backend, backend_id) in backends:
            modname = backend + '_backend.transformrunner'
            backend = importlib.import_module(modname)
            transformrunner = backend.TransformRunner(transform, backend_id, self.dry_run, self.verbosity)
            if self.verbosity >= 2:
                self.stdout.write('    -> calling backend: ' + modname)
            #process = multiprocessing.Process(target=transformrunner.run, args=())
            transformrunner.run()
