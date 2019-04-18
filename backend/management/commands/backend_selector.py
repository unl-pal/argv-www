import re
import importlib

from django.core.management.base import BaseCommand
from website.models import ProjectSelector, Filter, FilterDetail, Dataset
from django.conf import settings

class Command(BaseCommand):
    help = 'Sends filters to chosen backend'

    def add_arguments(self, parser):
        parser.add_argument('backend_name', type=str)
        parser.add_argument('selector_id', type=int)

    def handle(self, *args, **kwargs):
        backend_name = kwargs['backend_name']
        selector_id = kwargs['selector_id']
        project_selector = ProjectSelector.objects.get(pk=selector_id)
        # Test backend included
        backend = importlib.import_module(backend_name)
        formatter = backend.Formatter()
        formatter.hiworld()
