import socket

from django.utils import timezone

from website.choices import *
from website.models import TransformedProject


class TransformRunner:
    def __init__(self, transformed_project, transform, backend_id, dry_run, verbosity):
        self.transformed_project = transformed_project
        self.transform = transform
        self.backend_id = backend_id
        self.dry_run = dry_run
        self.verbosity = verbosity
        self.host = socket.gethostname()

    def done(self):
        if self.dry_run:
            return
# TODO only mark done if everything processed?
        self.transformed_project.status = PROCESSED
        self.transformed_project.datetime_processed = timezone.now()
        self.transformed_project.save()

    def all_projects(self):
        if self.transformed_project.src_selector:
            return self.transformed_project.src_selector.project.filter(path__isnull=False)
        return self.transformed_project.src_transformer.transformed_projects.filter(path__isnull=False)

    def projects(self):
        return self.all_projects().filter(host=self.host)

    def run(self):
        raise NotImplementedError('transform runners must override the run() method')

    def finish_project(self, proj, path = None):
        if self.verbosity >= 3:
            if not path:
                print("project transform failed: " + proj.path)
            else:
                print("saving project transform for: " + proj.path)
        if self.dry_run:
            return

        p, created = TransformedProject.objects.get_or_create(transform=self.transform, project=proj)

        p.host = self.host
        p.path = path
        p.datetime_processed = timezone.now()
        p.save()
