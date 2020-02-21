from django.core.management.base import BaseCommand
from website.models import Filter, Transform

'''Disable Backend

Usage: manage.py disable BACKEND_NAME
Disables all items in the database associated with the specified backend.
'''
class Command(BaseCommand):
    help = 'Disables all filters of certain backend'

    def add_arguments(self, parser):
        parser.add_argument('backend')

    def handle(self, *args, **options):
        backend = options['backend']
        for myfilter in Filter.objects.filter(associated_backend__name=backend):
            myfilter.enabled = False
            myfilter.save()
        for mytransform in Transform.objects.filter(associated_backend__name=backend):
            mytransform.enabled = False
            mytransform.save()
