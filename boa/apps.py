from django.apps import AppConfig
from django.core import management

class BoaConfig(AppConfig):
    name = 'boa'

    def ready(self):
        management.call_command('loaddata', self.name.lower() + '_install')
