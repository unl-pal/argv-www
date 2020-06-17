import datetime
import os
import uuid

from django.contrib.auth.models import User
from django.db import models
from django.dispatch import receiver
from django.utils import timezone
from django.utils.safestring import mark_safe
from django_countries.fields import CountryField

from backend.models import Backend

from .choices import *
from .validators import validate_file_size, validate_gh_token

# This line checks for duplicate email addresses when submiting forms that register/update email addresses
User._meta.get_field('email')._unique = True

# This function was added to prevent a weird duplication issue where any file uploaded without spaces would create duplicates even with signals
# checking filenames.  For some reason, files with spaces in their names would work correctly.  This function converts each
# filename into a UUID based (hopefully unique) filename.
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
    token = models.CharField(max_length=40, default='', validators=[validate_gh_token], help_text=mark_safe('Be sure to first <a href="https://github.com/settings/tokens/new" target="_blank">generate a GitHub personal access token</a> and <b>check public_repo</b>.'), blank=True)
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
        if self.user.first_name or self.user.last_name:
            return self.user.first_name + ' ' + self.user.last_name
        return self.user.username

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
    def get_by_natural_key(self, name):
        return self.get(name=name)

class Filter(models.Model):
    name = models.CharField(max_length=200, default='')
    val_type = models.CharField(max_length=10, choices=TYPE_CHOICES, default=INT)
    default_val = models.CharField(max_length=100, default='Enter value here')
    help_text = models.CharField(max_length=200, default='')
    enabled = models.BooleanField(default=False)

    objects = FilterManager()

    def is_int(self):
        return self.val_type in self.INT

    def is_string(self):
        return self.val_type in self.STRING

    def is_list(self):
        return self.val_type in self.LIST

    def __str__(self):
        return self.name

    def natural_key(self):
        return (self.name,)

class BackendFilterManager(models.Manager):
    def get_by_natural_key(self, flter, backend):
        return self.get(flter=flter, backend=backend)

class BackendFilter(models.Model):
    enabled = models.BooleanField(default=False)
    flter = models.ForeignKey(Filter, on_delete=models.PROTECT)
    backend = models.ForeignKey(Backend, on_delete=models.PROTECT)

    objects = BackendFilterManager()

    class Meta:
        unique_together = [['flter', 'backend']]

    def __str__(self):
        return self.flter.name

    def natural_key(self):
        return (self.flter, self.backend)

class Project(models.Model):
    dataset = models.ForeignKey(Dataset, on_delete=models.PROTECT)
    url = models.URLField(max_length=1000)

    def __str__(self):
        return self.url

@receiver(models.signals.pre_delete, sender=Project)
def handle_deleted_profile(sender, instance, **kwargs):
    instance.snapshots.all().delete()

class ProjectSnapshot(models.Model):
    project = models.ForeignKey(Project, related_name='snapshots', on_delete=models.PROTECT)
    host = models.CharField(max_length=1000, null=True, blank=True)
    path = models.CharField(max_length=5000, null=True, blank=True)
    datetime_processed = models.DateTimeField(null=True, blank=True)

    # repo metrics
    commits = models.BigIntegerField(null=True, blank=True)
    committers = models.BigIntegerField(null=True, blank=True)
    src_files = models.BigIntegerField(null=True, blank=True)

    def __str__(self):
        return str(self.project.url) + ' - ' + str(self.host) + ':' + str(self.path)

class ProjectSelector(models.Model):
    slug = models.SlugField(unique=True)
    input_dataset = models.ForeignKey(Dataset, on_delete=models.PROTECT, blank=False, null=False)
    user = models.ForeignKey(User, on_delete=models.PROTECT)
    pfilter = models.ManyToManyField(BackendFilter, blank=True, through='FilterDetail')
    status = models.CharField(choices=PROCESS_STATUS, default=READY, max_length=255)
    created = models.DateField(auto_now_add=True)
    parent = models.ForeignKey('self', related_name='children', on_delete=models.SET_NULL, blank=True, null=True)
    enabled = models.BooleanField(default=True)
    projects = models.ManyToManyField(ProjectSnapshot, through='Selection')
    submitted = models.DateTimeField(auto_now_add=True)
    fin_process = models.DateTimeField(auto_now=True)

    class Meta:
        permissions = [
            ('view_disabled_selectors', 'Can view disabled project selectors')
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
        return self.enabled and self.status == PROCESSED

    def project_count(self):
        return self.result_projects().count()

    def result_projects(self):
        return self.projects.filter(selection__retained=True)

class Selection(models.Model):
    project_selector = models.ForeignKey(ProjectSelector, on_delete=models.CASCADE)
    snapshot = models.ForeignKey(ProjectSnapshot, on_delete=models.PROTECT, null=True, blank=True)
    retained = models.BooleanField(blank=True, null=True)

    def __str__(self):
        return str(self.project_selector) + ' - ' + str(self.snapshot)

    class Meta:
        indexes = [
            models.Index(fields=['project_selector'], name='project_selector_key'),
        ]

class FilterDetail(models.Model):
    project_selector = models.ForeignKey(ProjectSelector, on_delete=models.CASCADE)
    pfilter = models.ForeignKey(BackendFilter, on_delete=models.CASCADE)
    value = models.TextField(max_length=1000, default='1')
    status = models.CharField(choices=PROCESS_STATUS, default=READY, max_length=255)
    fin_process = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.value

class TransformManager(models.Manager):
    def get_by_natural_key(self, name):
        return self.get(name=name)

class Transform(models.Model):
    name = models.CharField(max_length=200, unique=True, default='')
    enabled = models.BooleanField(default=False)
    associated_backend = models.ForeignKey(Backend, on_delete=models.PROTECT)

    objects = TransformManager()

    def __str__(self):
        return self.name

    def natural_key(self):
        return (self.name,)

class TransformParameter(models.Model):
    transform = models.ForeignKey(Transform, on_delete=models.CASCADE)
    name = models.CharField(max_length=200, default='')
    val_type = models.CharField(max_length=10, choices=TYPE_CHOICES, default=INT)
    default_val = models.CharField(max_length=100, default='Enter value here')
    help_text = models.CharField(max_length=200, default='')

    def is_int(self):
        return self.val_type in self.INT

    def is_string(self):
        return self.val_type in self.STRING

    def is_list(self):
        return self.val_type in self.LIST

    def __str__(self):
        return self.name

class TransformOption(models.Model):
    transform = models.ForeignKey(Transform, on_delete=models.CASCADE)
    parameters = models.ManyToManyField(TransformParameter, blank=True, through='TransformParameterValue')

    def __str__(self):
        return self.transform.name

class TransformParameterValue(models.Model):
    parameter = models.ForeignKey(TransformParameter, on_delete=models.CASCADE, related_name='value')
    option = models.ForeignKey(TransformOption, on_delete=models.CASCADE)
    value = models.TextField(max_length=1000, default='1')

class TransformedProject(models.Model):
    host = models.CharField(max_length=255)
    path = models.CharField(max_length=5000, null=True, blank=True)
    datetime_processed = models.DateTimeField(null=True, blank=True)
    transform = models.ForeignKey(TransformOption, on_delete=models.PROTECT)
    src_project = models.ForeignKey(ProjectSnapshot, on_delete=models.PROTECT, blank=True, null=True)
    src_transform = models.ForeignKey('self', on_delete=models.PROTECT, blank=True, null=True)

    def __str__(self):
        if self.src_project:
            return 'transformed project:' + str(self.src_project)
        return 'transformed transform:' + str(self.src_transform)

class ProjectTransformer(models.Model):
    slug = models.SlugField(unique=True)
    src_selector = models.ForeignKey(ProjectSelector, on_delete=models.PROTECT, blank=True, null=True)
    src_transformer = models.ForeignKey('self', on_delete=models.PROTECT, blank=True, null=True)
    user = models.ForeignKey(User, on_delete=models.PROTECT)
    status = models.CharField(max_length=255, choices=PROCESS_STATUS, default=READY)
    created = models.DateTimeField(auto_now_add=True)
    datetime_processed = models.DateTimeField(auto_now=True)
    transform = models.ForeignKey(TransformOption, on_delete=models.PROTECT)
    transformed_projects = models.ManyToManyField(TransformedProject, through='TransformSelection')
    parent = models.ForeignKey('self', related_name='children', on_delete=models.SET_NULL, blank=True, null=True)
    enabled = models.BooleanField(default=True)

    class Meta:
        permissions = [
            ('view_disabled_transforms', 'Can view disabled project transforms')
        ]

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

    def input_project_count(self):
        if self.src_selector:
            return self.src_selector.project_count()
        return self.src_transformer.project_count()

    def project_count(self):
        return self.result_projects().count()

    def result_projects(self):
        return self.transformed_projects.filter(path__isnull=False)

    def isDone(self):
        return self.enabled and self.status == PROCESSED

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
