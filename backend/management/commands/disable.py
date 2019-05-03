from django.core.management.base import BaseCommand
from website.models import Filter

class Command(BaseCommand):
    help = 'Installs specified backend'

    def add_arguments(self, parser):
        parser.add_argument('backend')
    
    def handle(self, *args, **options):
        backend = options['backend']
        for myfilter in Filter.objects.all().filter(associated_backend__name=backend):
            myfilter.enabled = False
            myfilter.save()
