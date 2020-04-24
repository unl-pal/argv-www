import importlib
import multiprocessing
import time

from django.conf import settings
from django.core.management.base import BaseCommand

from website.choices import *
from website.models import ProjectTransformer


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
                self.process_transform(ProjectTransformer.objects.get(slug=slug))
            except:
                self.stdout.write('error processing: ' + slug)

        if len(options['slug']) == 0:
            while True:
                transform = ProjectTransformer.objects.filter(enabled=True, status=READY).first()
                if transform:
                    self.process_transform(transform)

                if self.no_poll:
                    break
                time.sleep(3)

    def process_transform(self, transform):
        self.stdout.write('processing ProjectTransformer: ' + transform.slug)
        if not self.dry_run:
            transform.status = ONGOING
            transform.save()

        options = transform.transform

        modname = str(options.transform.associated_backend) + '_backend.transformrunner'
        backend = importlib.import_module(modname)

        transformrunner = backend.TransformRunner(transform, options, options.transform.associated_backend, self.dry_run, self.verbosity)
        if self.verbosity >= 2:
            self.stdout.write('    -> calling backend: ' + modname)
        #process = multiprocessing.Process(target=transformrunner.run, args=())
        transformrunner.run()
