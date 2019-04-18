import subprocess

from django.core.management.base import BaseCommand
from website.models import ProjectSelector, Filter, FilterDetail, Dataset
from django.conf import settings

class Command(BaseCommand):
    help = 'Gets data from model'

    def add_arguments(self, parser):
        parser.add_argument('backend_name', type=str)

    def handle(self, *args, **kwargs):
        backend_name = kwargs['backend_name']
        try:
            subprocess.run(['python','manage.py','loaddata', backend_name])
            app_config = backend_name + '.apps.' + backend_name.capitalize() + 'Config'
            if app_config not in settings.INSTALLED_APPS:
                settings.INSTALLED_APPS.append(config)
        except:
            print("The specified backend " + backend_name + " does not exist.")
