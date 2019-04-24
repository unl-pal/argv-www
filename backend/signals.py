from pathlib import Path
from shutil import copyfile

from django.dispatch import receiver
from django.db.models.signals import post_save
from django.core import management
from .models import Backend

@receiver(post_save, sender=Backend)
def enableBackend(sender, instance, created, **kwargs):
    if created:
        if instance.enable:
            source = './' + instance.name.lower() + '/fixtures/filters.json'
            dest = './backend/fixtures/' + instance.name.lower() + '_filters.json'
            copyfile(source, dest)
            management.call_command('loaddata', instance.name.lower() + '_filters')
