import time

from django.core.management.base import BaseCommand
from website.models import ProjectSelector

'''Run Puller

Usage: manage.py runpuller
Runs a boa puller in the background to grab unprocessed project selections from database
'''
class Command(BaseCommand):
    help = 'Runs a boa puller in the background to grab unprocessed project selections from database'
    
    def handle(self, *args, **options):
        while(True):
            selectors = ProjectSelector.objects.all().filter(processed='READY')
            print(selectors)
            time.sleep(3)
