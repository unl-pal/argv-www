import os

from django.contrib.auth.models import User
from django.db.models.signals import post_save, post_delete, pre_save
from django.dispatch import receiver
from django.conf import settings
from .models import Profile

@receiver(post_save, sender=User)
def createUserProfile(sender, instance, created, **kwargs):
    if created:
        Profile.objects.create(user=instance)

@receiver(post_save, sender=User)
def saveUserProfile(sender, instance, **kwargs):
    instance.profile.save()

def removeProfilePhoto(photo):
    if os.path.basename(photo.name) != "defaultuser.png":
        if os.path.isfile(photo.path):
            try:
                os.remove(photo.path)
                return True
            except:
                return False
    return False

@receiver(post_delete, sender=Profile)
def deleteOnDelete(sender, instance, **kwargs):
    if instance.photo:
        removeProfilePhoto(instance.photo)
        return True
    return False

@receiver(pre_save, sender=Profile)
def deleteOnChange(sender, instance, **kwargs):
    if not instance.pk:
        return False

    try:
        oldPhoto = Profile.objects.get(pk=instance.pk).photo
    except Profile.DoesNotExist:
        return False

    newPhoto = instance.photo
    if not newPhoto == oldPhoto:
        removeProfilePhoto(oldPhoto)
        return True
    return False
