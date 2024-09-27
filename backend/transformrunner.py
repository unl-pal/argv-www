import socket

from django.conf import settings
from django.utils import timezone

from website.choices import PROCESSED, ONGOING
from website.models import TransformedProject, TransformSelection, ProjectTransformer

from decouple import config


class TransformRunner:
    def __init__(self, transformed_project, transform, dry_run, verbosity):
        self.transformed_project = transformed_project
        self.transform = transform
        self.dry_run = dry_run
        self.verbosity = verbosity

        self.host = socket.gethostname()

        self.repo_path = getattr(settings, 'REPO_PATH')
        self.transformed_path = getattr(settings, 'TRANSFORMED_PATH')

    def done(self):
        if not self.dry_run and not self.all_remaining_projects().exists():
            self.transformed_project.status = PROCESSED
            self.transformed_project.datetime_processed = timezone.now()
            self.transformed_project.save()

    def all_projects(self):
        return self.transformed_project.input_projects()

    def projects(self):
        return self.all_projects().filter(host=self.host)

    def all_finished_projects(self):
        if self.transformed_project.src_selector:
            return self.transformed_project.transformed_projects.select_related('src_project')
        return self.transformed_project.transformed_projects.select_related('src_transformer')

    def finished_projects(self):
        return self.all_finished_projects().filter(host=self.host)

    def all_remaining_projects(self):
        if self.transformed_project.src_selector:
            return self.all_projects().exclude(pk__in=self.all_finished_projects().values_list('src_project__pk', flat=True))
        return self.all_projects().exclude(pk__in=self.all_finished_projects().values_list('src_transform__pk', flat=True))

    def remaining_projects(self):
        if self.transformed_project.src_selector:
            return self.projects().exclude(pk__in=self.finished_projects().values_list('src_project__pk', flat=True))
        return self.projects().exclude(pk__in=self.finished_projects().values_list('src_transform__pk', flat=True))

    def debug(self):
        self.run()

    def run(self):
        raise NotImplementedError('transform runners must override the run() method')

    def finish_project(self, proj, istransformed, path = None):
        if self.verbosity >= 3:
            if not path:
                print("project transform failed: " + str(proj))
            else:
                print("saving project transform for: " + str(proj))
        if self.dry_run:
            return

        if istransformed:
            p, _ = TransformedProject.objects.get_or_create(transform=self.transform, src_transform=proj)
        else:
            p, _ = TransformedProject.objects.get_or_create(transform=self.transform, src_project=proj)

        p.host = self.host
        p.path = path
        p.datetime_processed = timezone.now()
        p.save()

        TransformSelection.objects.get_or_create(transformer=self.transformed_project,transformed_project=p)


### Transformation Task

from argv.celery import app
from celery.utils.log import get_task_logger
celery_logger = get_task_logger(__name__)

import importlib

TRANSFORM_DRY_RUN = config('TRANSFORM_DRY_RUN', default=False, cast=bool)
TRANSFORM_VERBOSITY = config('TRANSFORM_VERBOSITY', default=1, cast=int)
TRANSFORM_DEBUG = config('TRANSFORM_DEBUG', default=False, cast=bool)

@app.task
def run_transforms(transform_slug):
    transform = ProjectTransformer.objects.get(slug=transform_slug)
    if not TRANSFORM_DRY_RUN:
        transform.status = ONGOING
        transform.save(update_fields=['status',])

    options = transform.transform

    module_name = str(options.transform.associated_backend) + '_backend.transformrunner'
    backend = importlib.import_module(module_name)

    transform_runner = backend.TransformRunner(transform, options, TRANSFORM_DRY_RUN, TRANSFORM_VERBOSITY)
    celery_logger.info(f'    -> calling backend: {module_name}')

    if TRANSFORM_DEBUG:
        transform_runner.debug()
    else:
        transform_runner.run()

