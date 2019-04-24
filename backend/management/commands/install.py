from shutil import copyfile

from django.core import management
from django.core.management.base import BaseCommand
from backend.models import Backend

class Command(BaseCommand):
    help = 'Installs specified backend'

    def add_arguments(self, parser):
        parser.add_argument('backend_name', type=str)

    def handle(self, *args, **kwargs):
        backend_name = kwargs['backend_name']
        source = './' + backend_name.lower() + '/fixtures/install.json'
        dest = './backend/fixtures/' + backend_name.lower() + '_install.json'
        copyfile(source, dest)
        management.call_command('loaddata', backend_name.lower() + '_install')
