from django.core.management.base import BaseCommand
from website.models import Paper

class Command(BaseCommand):
    help = 'Gets data from model'

    def handle(self, *args, **kwargs):
        print(Paper.objects.all())
