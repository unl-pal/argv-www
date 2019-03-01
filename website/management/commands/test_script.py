from django.core.management.base import BaseCommand
from website.models import Paper
from website.management.scripts.other_script import helper_function

class Command(BaseCommand):
    help = 'Gets data from model'

    def handle(self, *args, **kwargs):
        value = helper_function('hello world')
        print(value)
        print(Paper.objects.all())
