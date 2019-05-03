from django.core.management.base import BaseCommand
from backend.models import Backend

class Command(BaseCommand):
    help = 'Installs specified backend'

    def add_arguments(self, parser):
        parser.add_argument('backend')
    
    def handle(self, *args, **options):
        backend = options['backend']
        backend = Backend.objects.get(name=backend)
        backend.enabled = False
        backend.save()
