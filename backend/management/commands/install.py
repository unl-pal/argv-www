from django.core.management.base import BaseCommand
from django.core.management import call_command

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
        call_command('loaddata', backend + '_install')
