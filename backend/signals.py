from django.dispatch import receiver
from django.db.models.signals import post_save
from django.core import management
from .models import Backend

@receiver(post_save, sender=Backend)
def enableBackend(sender, instance, created, **kwargs):
    if not created:
        if instance.enable:
            management.call_command('loaddata', instance.name.lower() + '_filters')
