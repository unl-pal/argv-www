import socket

from django.utils import timezone
from website.models import TransformedProject
from website.choices import *

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
        #if not self.transformed_project.transforms_set.exclude(status=PROCESSED).exists():
        self.transformed_project.status = PROCESSED
        self.transformed_project.datetime_processed = timezone.now()
        #else:
        #    self.transformed_project.status = READY
        self.transformed_project.save()

    def all_projects(self):
        return self.transformed_project.project_selector.project.filter(path__isnull=False)

    def projects(self):
        return self.all_projects().filter(host=self.host)

    def run(self):
        raise NotImplementedError('transform runners must override the run() method')

    def finish_project(self, proj, path = None):
        if self.verbosity >= 3:
            if not path:
                print("project transform failed: " + proj.url)
            else:
                print("saving project transform: " + proj.url)
        if self.dry_run:
            return

        p, created = TransformedProject.objects.get_or_create(transform=self.transform, project=proj)

        p.host = self.host
        p.path = path
        p.datetime_processed = timezone.now()
        p.save()
