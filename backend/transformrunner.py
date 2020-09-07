import socket

from django.conf import settings
from django.utils import timezone

from website.choices import PROCESSED
from website.models import TransformedProject, TransformSelection


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
