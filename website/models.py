import uuid
import datetime
import os

from django.db import models
from django.contrib.auth.models import User
from django.utils.safestring import mark_safe
from django.utils import timezone
from backend.models import Backend
from django_countries.fields import CountryField
from .validators import validate_file_size, validate_gh_token
from .choices import *

# This line checks for duplicate email addresses when submiting forms that register/update email addresses
User._meta.get_field('email')._unique = True

# This function was added to prevent a weird duplication issue where any file uploaded without spaces would create duplicates even with signals
# checking filenames.  For some reason, files with spaces in their names would work correctly.  This function adds a _1 to the end of each 
# filename.
def get_filename(instance, filename):
    filename, ext = os.path.splitext(filename)
    filename = str(uuid.uuid4())
    filename.replace('-', '')
    filename += ext
    return '{0}/{1}'.format(instance.user.id, filename)

class Paper(models.Model):
    author = models.CharField(max_length=250, default='')
    title = models.CharField(max_length=250, default='')
    date = models.DateField(default=datetime.date.today)
    publish = models.CharField(max_length=250, default='')
    link = models.CharField(max_length=1000, default='')

    class Meta:
        ordering = ['-date']

    def __str__(self):
        return self.title + ' - ' + self.author

class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    photo = models.ImageField(upload_to=get_filename, default='defaultuser.png', validators=[validate_file_size])
    bio = models.TextField(max_length=1000, blank=True)
    token = models.CharField(max_length=40, default='', validators=[validate_gh_token], help_text=mark_safe('Be sure to first <a href="https://github.com/settings/tokens">generate a GitHub personal access token</a>.'))
    sharetoken = models.BooleanField(default=False, help_text='Note: the token is never shared/visible to other users!')
    privacy_agreement = models.BooleanField(default=False)
    terms_agreement = models.BooleanField(default=False)
    age_confirmation = models.BooleanField(default=False)
    active_email = models.BooleanField(default=False)
    country = CountryField()
    honorific = models.CharField(
        max_length=5,
        choices=HONORIFIC_CHOICES,
        default=NONE,
        blank=True
    )
    staffStatus = models.CharField(max_length=15, choices=STAFF_STATUS, default=USER)

    def hasBio(self):
        return self.staffStatus != USER

    def isAdmin(self):
        return self.staffStatus == ADMIN

    def isModerator(self):
        return self.staffStatus == MODERATOR

    def isRetired(self):
        return self.staffStatus == RETIRED

    def __str__(self):
        return self.user.first_name

    class Meta:
        permissions = (
            ('Admin', 'A faculty member working on the project.'),
            ('Moderator', 'An assistant currently working on the project.'),
            ('Retired', 'Previous researchers on project.'),
        )

class Dataset(models.Model):
    name = models.CharField(max_length=200, default='')

    def __str__(self):
        return self.name

class FilterManager(models.Manager):
    def get_by_natural_key(self, name, backend):
        return self.get(name=name, associated_backend=backend)

class Filter(models.Model):
    name = models.CharField(max_length=200, default='')
    val_type = models.CharField(max_length=10, choices=TYPE_CHOICES, default=INT)
    default_val = models.CharField(max_length=100, default='Enter value here')
    enabled = models.BooleanField(default=False)
    associated_backend = models.ForeignKey(Backend, on_delete=models.PROTECT)

    objects = FilterManager()

    class Meta:
        unique_together = [['name', 'associated_backend']]

    def is_int(self):
        return self.val_type in self.INT

    def is_string(self):
        return self.val_type in self.STRING

    def is_list(self):
        return self.val_type in self.LIST

    def __str__(self):
        return self.name

    def natural_key(self):
        return (self.name, self.associated_backend)

class Project(models.Model):
    dataset = models.ForeignKey(Dataset, on_delete=models.PROTECT)
    url = models.URLField(max_length=1000)
    host = models.CharField(max_length=1000, null=True, blank=True)
    path = models.CharField(max_length=5000, null=True, blank=True)
    datetime_processed = models.DateTimeField(null=True, blank=True)

    def __str__(self):
        return self.url

class ProjectSelector(models.Model):
    slug = models.SlugField(unique=True)
    input_dataset = models.ForeignKey(Dataset, on_delete=models.PROTECT, blank=False, null=False)
    user = models.ForeignKey(User, on_delete=models.PROTECT)
    pfilter = models.ManyToManyField(Filter, blank=True, through='FilterDetail')
    status = models.CharField(choices=PROCESS_STATUS, default=READY, max_length=255)
    created = models.DateField(auto_now_add=True)
    parent = models.ForeignKey('self', on_delete=models.CASCADE, blank=True, null=True)
    enabled = models.BooleanField(default=True)
    project = models.ManyToManyField(Project, through='Selection')
    submitted = models.DateTimeField(auto_now_add=True)
    fin_process = models.DateTimeField(auto_now=True)

    class Meta:
        permissions = [
            ('view_disabled', 'Can view disabled project selectors')
        ]

    def save(self, **kwargs):
        if self.pk == None:
            self.slug = self.gen_slug()
        super().save(**kwargs)

    """ Generates a unique slug to be used for sharing

        Arguments: none, returns string(slug)
        Creates a unique id to be used for project sharing.
        Uses primary key to generate unique key.
        Checks for collisions using check_collision(slug).
        Does not return until unique slug is generated.    
    """
    def gen_slug(self):
        slug = str(uuid.uuid4())
        slug = slug.replace('-','')
        return slug

    def __str__(self):
        return self.slug

    def isDone(self):
        if self.status != PROCESSED:
            return False
        return not ProjectTransformer.objects.filter(project_selector=self.pk).exclude(status=PROCESSED).exists()

class Selection(models.Model):
    project_selector = models.ForeignKey(ProjectSelector, on_delete=models.CASCADE)
    project = models.ForeignKey(Project, on_delete=models.CASCADE)

    def __str__(self):
        return self.project_selector.slug
    
    class Meta:
        indexes = [
            models.Index(fields=['project_selector'], name='project_selector_key'),
        ]

class FilterDetail(models.Model):
    project_selector = models.ForeignKey(ProjectSelector, on_delete=models.CASCADE)
    pfilter = models.ForeignKey(Filter, on_delete=models.CASCADE)
    value = models.TextField(max_length=1000, default='1')
    status = models.CharField(choices=PROCESS_STATUS, default=READY, max_length=255)
    fin_process = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.value

class Transform(models.Model):
    name = models.CharField(max_length=200, default='')
    enabled = models.BooleanField(default=False)
    associated_backend = models.ForeignKey(Backend, on_delete=models.PROTECT)

    def __str__(self):
        return self.name

class TransformOption(models.Model):
    transform = models.ForeignKey(Transform, on_delete=models.CASCADE)

    def __str__(self):
        return self.transform.name

class TransformedProject(models.Model):
    host = models.CharField(max_length=255)
    path = models.CharField(max_length=5000, null=True, blank=True)
    datetime_processed = models.DateTimeField(null=True, blank=True)
    transform = models.ForeignKey(TransformOption, on_delete=models.PROTECT)
    project = models.ForeignKey(Project, on_delete=models.PROTECT, blank=True, null=True)
    parent = models.ForeignKey('self', on_delete=models.CASCADE, blank=True, null=True)

    def __str__(self):
        return self.project.url

class ProjectTransformer(models.Model):
    slug = models.SlugField(unique=True)
    project_selector = models.ForeignKey(ProjectSelector, on_delete=models.PROTECT)
    user = models.ForeignKey(User, on_delete=models.PROTECT)
    status = models.CharField(max_length=255, choices=PROCESS_STATUS, default=READY)
    datetime_processed = models.DateTimeField(auto_now=True)
    transforms = models.ManyToManyField(TransformOption, blank=True)
    transformed_projects = models.ManyToManyField(TransformedProject, through='TransformSelection')
    parent = models.ForeignKey('self', on_delete=models.CASCADE, blank=True, null=True)

    def __str__(self):
        return self.slug
    
    def save(self, **kwargs):
        if self.pk == None:
            self.slug = self.gen_slug()
        super().save(**kwargs)

    def gen_slug(self):
        slug = str(uuid.uuid4())
        slug = slug.replace('-','')
        return slug

class TransformSelection(models.Model):
    transformer = models.ForeignKey(ProjectTransformer, on_delete=models.CASCADE)
    transformed_project = models.ForeignKey(TransformedProject, on_delete = models.CASCADE)

    def __str__(self):
        return self.transformer.slug

class UserAuthAuditEntry(models.Model):
    action = models.CharField(max_length=16)
    ip = models.GenericIPAddressField(null=True)
    datetime = models.DateTimeField(default=timezone.now)
    user = models.ForeignKey(User, on_delete=models.SET_NULL, blank=True, null=True)
    attempted = models.CharField(max_length=256, null=True)
    hijacker  = models.ForeignKey(User, on_delete=models.SET_NULL, blank=True, null=True, related_name='hijacker')
    hijacked  = models.ForeignKey(User, on_delete=models.SET_NULL, blank=True, null=True, related_name='hijacked')

    def __unicode__(self):
        return self.__str__()

    def __str__(self):
        return '{0} - {1}/{2}/{3} - {4}/{5}/{6}'.format(self.action, self.datetime, self.ip, self.user, self.attempted, self.hijacker, self.hijacked)
