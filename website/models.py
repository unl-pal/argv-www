from django.db import models
from django.contrib.auth.models import User
from django.db.models.signals import post_save, post_delete, pre_save
from django.dispatch import receiver

# Create your models here.
class Papers(models.Model):
    author = models.CharField(max_length=250, default="")
    title = models.CharField(max_length=250, default="")
    year = models.IntegerField(default=None)
    publish = models.CharField(max_length=250, default="")
    link = models.CharField(max_length=1000, default="")
    upload = models.FileField(default=None)

    def __str__(self):
        return self.title + ' - ' + self.author

class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    photo = models.ImageField(default='defaultuser.png')
    bio = models.TextField(max_length=1000, blank=True)
    NONE = '--'
    DOCTOR = 'Dr.'
    SURNAME_CHOICES = (
        (NONE, ''),
        (DOCTOR, 'DR'),
    )
    surname = models.CharField(
        max_length=5,
        choices=SURNAME_CHOICES,
        default=NONE,
    )
    NONE = '--'
    STUDENT = 'Student'
    FACULTY = 'Faculty'
    STUDENT_OR_FACULTY = (
        (NONE, ''),
        (STUDENT, 'STUDENT'),
        (FACULTY, 'FACULTY'),
    )
    studentorfaculty = models.CharField(
        max_length=15,
        choices=STUDENT_OR_FACULTY,
        default=NONE,
    )

    def isFaculty(self):
        return self.studentorfaculty in (self.FACULTY)

    def isDoctor(self):
        return self.surname in (self.DOCTOR)

    def __str__(self):
        return self.user.first_name

@receiver(post_save, sender=User)
def createUserProfile(sender, instance, created, **kwargs):
    if created:
        Profile.objects.create(user=instance)

@receiver(post_save, sender=User)
def saveUserProfile(sender, instance, **kwargs):
    instance.profile.save()


@receiver(post_delete, sender=Profile)
def deleteOnDelete(sender, instance, **kwargs):
    if instance.photo:
        if os.path.basename(instance.photo.name) != "defaultuser.png":
            if os.path.isfile(instance.photo.path):
                os.remove(instance.photo.path)

@receiver(pre_save, sender=Profile)
def deleteOnChange(sender, instance, **kwargs):
    if not instance.pk:
        return False

    try:
        oldPhoto = Profile.objects.get(pk=instance.pk).photo
    except Profile.DoesNotExist:
        return False

    newPhoto = instance.photo
    if os.path.basename(oldPhoto.name) == "defaultuser.png":
        return False
    else:
        if os.path.isfile(oldPhoto.path):
            os.remove(oldPhoto.path)