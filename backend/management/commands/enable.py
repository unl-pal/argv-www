from django.core.management import call_command
from django.core.management.base import BaseCommand

from website.models import BackendFilter, Filter, Transform


'''Enable Command

Usage: manage.py enable BACKEND_NAME
Installs and enables all items in the database associated with the specified backend.
'''
class Command(BaseCommand):
    help = 'Enables the given backend'

    def add_arguments(self, parser):
        parser.add_argument('backend')

    def handle(self, *args, **options):
        backend = options['backend']

        for myfilter in BackendFilter.objects.filter(backend__name=backend):
            myfilter.enabled = True
            myfilter.save()
        for myfilter in Filter.objects.all():
            myfilter.enabled = BackendFilter.objects.filter(flter=myfilter,enabled=True).exists()
            myfilter.save()

        for mytransform in Transform.objects.filter(associated_backend__name=backend):
            mytransform.enabled = True
            mytransform.save()
