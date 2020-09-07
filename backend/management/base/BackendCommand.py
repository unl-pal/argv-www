from django.core.management.base import BaseCommand


'''Base command for anything that works on a backend module'''
class BackendCommand(BaseCommand):
    def add_arguments(self, parser):
        parser.add_argument('backend')

    def handle(self, *args, **options):
        self.handle_backend(options['backend'])

    def handle_backend(self, backend):
        pass