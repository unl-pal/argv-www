from django.core.management.base import BaseCommand
from django.core.management import call_command
from backend.models import Backend
from website.models import Filter


'''Uninstall Command

Usage: manage.py uninstall BACKEND_NAME
Disables the specified backend in the database.  Does not delete anything.
'''
class Command(BaseCommand):
    help = 'Uninstalls specified backend'

    def add_arguments(self, parser):
        parser.add_argument('backend')
    
    def handle(self, *args, **options):
        backend = options['backend']
        backend = Backend.objects.get(name=backend)
        backend.enabled = False
        backend.save()
        call_command('disable', backend)
