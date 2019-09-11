import time
import importlib
import multiprocessing

from django.core.management.base import BaseCommand
from website.models import ProjectSelector
from django.conf import settings

'''Run Puller

Usage: manage.py runpuller
Runs a boa puller in the background to grab unprocessed project selections from database
'''
class Command(BaseCommand):
    help = 'Runs a boa puller in the background to grab unprocessed project selections from database'
    
    def handle(self, *args, **options):
        while(True):
            selectors = ProjectSelector.objects.all().filter(processed='READY')
            for selector in selectors:
                selector.processed = 'ONGOING'
                selector.save()
                filters = selector.filterdetail_set.all()
                backends = []
                for pfilter in filters:
                    associated_backend = str(pfilter.pfilter.associated_backend)
                    if associated_backend not in backends:
                        backends.append(associated_backend)

                for backend in backends:
                    modname = associated_backend + '_backend.runner'
                    backend = importlib.import_module(modname)
                    runner = backend.Runner(selector)
                    process = multiprocessing.Process(target=runner.run, args=())
                selector.processed = 'PROCESSED'
                selector.save()
            time.sleep(3)
