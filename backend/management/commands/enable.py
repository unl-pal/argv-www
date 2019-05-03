from django.core.management.base import BaseCommand
from django.core.management import call_command
from website.models import Filter

class Command(BaseCommand):
    help = 'Enables filters for specified backend'

    def add_arguments(self, parser):
        parser.add_argument('backend')
    
    def handle(self, *args, **options):
        backend = options['backend']
        call_command('loaddata', backend + '_filters')
        for myfilter in Filter.objects.all().filter(associated_backend__name=backend):
            myfilter.enabled = True
            myfilter.save()
