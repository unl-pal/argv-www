from django.db import models
from django.contrib.auth.models import User
from django.db.models.signals import post_save
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