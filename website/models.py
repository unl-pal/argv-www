import uuid
import datetime
import os

from django.db import models
from django.contrib.auth.models import User
from django.utils.safestring import mark_safe
from .validators import validate_file_size, validate_gh_token

# This function was added to prevent a weird duplication issue where any file uploaded without spaces would create duplicates even with signals
# checking filenames.  For some reason, files with spaces in their names would work correctly.  This function adds a _1 to the end of each 
# filename.
def get_filename(instance, filename):
    filename, ext = os.path.splitext(filename)
    filename = str(uuid.uuid4())
    filename.replace("-", "")
    filename += ext
    return '{0}/{1}'.format(instance.user.id, filename)

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
    photo = models.ImageField(upload_to=get_filename, default='defaultuser.png', validators=[validate_file_size])
    bio = models.TextField(max_length=1000, blank=True)
    token = models.CharField(max_length=40, default="", validators=[validate_gh_token], help_text=mark_safe("Be sure to first <a href=\"https://github.com/settings/tokens\">generate a GitHub personal access token</a>."))
    sharetoken = models.BooleanField(default=False, help_text="Note: the token is never shared/visible to other users!")

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

class Selection(models.Model):
    name = models.CharField(max_length=200, default="")

    def __str__(self):
        return 'Selection'

class ProjectSelector(models.Model):
    input_dataset = models.ForeignKey(Dataset, on_delete=models.PROTECT, blank=True, null=True)
    input_selection = models.ForeignKey(Selection, related_name="input_selection", on_delete=models.PROTECT, blank=True, null=True)
    output_selection = models.ForeignKey(Selection, related_name="output_selection", on_delete=models.PROTECT)
    user = models.ForeignKey(User, on_delete=models.PROTECT)

    def __str__(self):
        return self.input_dataset + ' - ' + self.input_selection

class Filter(models.Model):
    project_selectors = models.ManyToManyField(ProjectSelector)
    name = models.CharField(max_length=200, default="")

    def __str__(self):
        return self.name

class ProjectTransformer(models.Model):
    input_selection = models.ForeignKey(Selection, on_delete=models.PROTECT)
    user = models.ForeignKey(User, on_delete=models.PROTECT)

    def __str__(self):
        return self.input_selection

class Transform(models.Model):
    project_transformers = models.ManyToManyField(ProjectTransformer)
    name = models.CharField(max_length=200, default="")

    def __str__(self):
        return self.name

class Analysis(models.Model):
    input_selection = models.ForeignKey(Selection, on_delete=models.PROTECT)
    user = models.ForeignKey(User, on_delete=models.PROTECT)

    def __str__(self):
        return self.input_selection
