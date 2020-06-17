import socket

from django.conf import settings
from django.utils import timezone

from website.choices import *
from website.models import TransformedProject


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
        # TODO only mark done if everything processed?
        if not self.dry_run:
            self.transformed_project.status = PROCESSED
            self.transformed_project.datetime_processed = timezone.now()
            self.transformed_project.save()

    def all_projects(self):
        if self.transformed_project.src_selector:
            return self.transformed_project.src_selector.projects.filter(path__isnull=False)
        return self.transformed_project.src_transformer.transformed_projects.filter(path__isnull=False)

    def projects(self):
        return self.all_projects().filter(host=self.host)

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
