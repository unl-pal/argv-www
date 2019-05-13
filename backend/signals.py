from django.dispatch import receiver
from django.db.models.signals import post_save
from django.core import management
from website.models import Filter
from .models import Backend

'''Enable Backend

Arguments:
-> sender: the object triggering the signal
-> instance: the object the function performs operations on
-> created: a boolean value showing if the sender was newly created
-> kwargs: any additional arguments
Triggers when enabling a backend on the admin page
If the object has not been newly installed, load all associated filters and enable them
'''
@receiver(post_save, sender=Backend)
def enableBackend(sender, instance, created, **kwargs):
    if not created:
        if instance.enable:
            management.call_command('loaddata', instance.name.lower() + '_filters')
            for myfilter in Filter.objects.all().filter(associated_backend__name=instance.name.lower()):
                myfilter.enabled = True
                myfilter.save()
