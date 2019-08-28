import time
import importlib

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
                filters = selector.filterdetail_set.all()
                for pfilter in filters:
                    associated_backend = str(pfilter.pfilter.associated_backend)
                    modname = associated_backend + '_backend'
                    modname += '.' + modname
                    backend = importlib.import_module(modname)
                    runner = backend.Runner()
                    runner.run()
            time.sleep(3)
