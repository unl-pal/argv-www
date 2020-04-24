from django.core.management.base import BaseCommand
from django.core.management import call_command
from backend.models import Backend

''' Install Command

Usage: manage.py install BACKEND_NAME
Finds fixture file with BACKEND_NAME value and loads the _install data into the database.
'''
class Command(BaseCommand):
    help = 'Installs specified backend'

    def add_arguments(self, parser):
        parser.add_argument('backend')

    def handle(self, *args, **options):
        backend = options['backend']

        try:
            call_command('loaddata', backend + '_install')
        except:
            pass

        try:
            call_command('loaddata', 'filters')
        except:
            pass

        try:
            call_command('loaddata', backend + '_filters')
        except:
            pass

        try:
            call_command('loaddata', backend + '_transforms')
        except:
            pass

        backend = Backend.objects.get(name=backend)
        backend.enabled = True
        backend.save()
