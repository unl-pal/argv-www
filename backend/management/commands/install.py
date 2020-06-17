from django.core.management import call_command

from backend.management.base.BackendCommand import BackendCommand
from backend.models import Backend


''' Install Command

Usage: manage.py install BACKEND_NAME
Finds fixture file with BACKEND_NAME value and loads the _install data into the database.
'''
class Command(BackendCommand):
    help = 'Installs specified backend'

    def handle_backend(self, backend):
        try:
            call_command('loaddata', 'filters')
        except:
            pass

        if not backend == 'none':
            try:
                call_command('loaddata', backend + '_install')
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
