from backend.management.base.BackendCommand import BackendCommand
from website.models import BackendFilter, Filter, Transform


'''Disable Backend

Usage: manage.py disable BACKEND_NAME
Disables all items in the database associated with the specified backend.
'''
class Command(BackendCommand):
    help = 'Disables the given backend'

    def handle_backend(self, backend):
        for myfilter in BackendFilter.objects.filter(backend__name=backend):
            myfilter.enabled = False
            myfilter.save()
        for myfilter in Filter.objects.all():
            myfilter.enabled = BackendFilter.objects.filter(flter=myfilter,enabled=True).exists()
            myfilter.save()

        for mytransform in Transform.objects.filter(associated_backend__name=backend):
            mytransform.enabled = False
            mytransform.save()
