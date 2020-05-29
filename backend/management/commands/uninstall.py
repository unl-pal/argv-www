from backend.management.base.BackendCommand import BackendCommand
from django.core.management import call_command
from backend.models import Backend
from website.models import Filter


'''Uninstall Command

Usage: manage.py uninstall BACKEND_NAME
Disables the specified backend in the database.  Does not delete anything.
'''
class Command(BackendCommand):
    help = 'Uninstalls specified backend'

    def handle_backend(self, backend):
        call_command('disable', backend)

        backend = Backend.objects.get(name=backend)
        backend.enabled = False
        backend.save()
