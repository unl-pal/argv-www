import datetime
from django.db import models
from django.contrib.auth.models import User
from django.db.models.signals import post_save
from django.dispatch import receiver

# Create your models here.
class Paper(models.Model):
    author = models.CharField(max_length=250, default="")
    title = models.CharField(max_length=250, default="")
    date = models.DateField(default=datetime.date.today)
    publish = models.CharField(max_length=250, default="")
    link = models.CharField(max_length=1000, default="")

    class Meta:
        ordering = ['-date']

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
    NONE = 'User'
    RETIRED = 'Retired'
    MODERATOR = 'Moderator'
    ADMIN = 'Admin'
    STAFF_STATUS = (
        (NONE, 'USER'),
        (RETIRED, 'RETIRED'),
        (MODERATOR, 'MODERATOR'),
        (ADMIN, 'ADMIN'),
    )

    staffStatus = models.CharField(max_length=15, choices=STAFF_STATUS, default=NONE)

    def isAdmin(self):
        return self.staffStatus in (self.ADMIN)

    def isModerator(self):
        return self.staffStatus in (self.MODERATOR)

    def isRetired(self):
        return self.staffStatus in (self.RETIRED)

    def isDoctor(self):
        return self.surname in (self.DOCTOR)

    def __str__(self):
        return self.user.first_name

    class Meta:
        permissions = (
            ("Admin", "A faculty member working on the project."),
            ("Moderator", "An assistant currently working on the project."),
            ("Retired", "Previous researchers on project."),
        )

class Dataset(models.Model):
    name = models.CharField(max_length=200, default="")

    def __str__(self):
        return self.name

class ProjectSelector(models.Model):
    input_dataset = models.ForeignKey(Dataset, blank=True, null=True)
    input_selection = models.ForeignKey(Selection, blank=True, null=True)
    output = models.IntegerField()

    def __str__(self):
        return self.input_dataset + ' - ' + self.input_selection

class Filter(models.Model):
    project_selector = models.ManyToMany(ProjectSelector, on_delete=models.CASCADE)
    name = models.CharField(max_length=200, default="")

    def __str__(self):
        return self.name

class ProjectTransformer(models.Model):
    input_selection = models.ForeignKey(Selection)

    def __str__(self):
        return self.input_selection

class Selection(models.Model):
    name = models.CharField(max_length=200, default="")

    def __str__(self):
        return 'Selection'

class Transform(models.Model):
    project_transformer = models.ManyToMany(ProjectTransformer)
    name = models.CharField(max_length=200, default="")

    def __str__(self):
        return self.name

class Analysis(models.Model):
    input_selection = models.ForeignKey(Selection)

    def __str__(self):
        return self.input_selection

@receiver(post_save, sender=User)
def createUserProfile(sender, instance, created, **kwargs):
    if created:
        Profile.objects.create(user=instance)

@receiver(post_save, sender=User)
def saveUserProfile(sender, instance, **kwargs):
    instance.profile.save()
