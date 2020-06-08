from django.utils import timezone

from website.choices import *
from website.models import Project, ProjectSelector, ProjectSnapshot, Selection


class DiscoveryRunner:
    '''Notes on attributes from selector

    filters = selector.filterdetail_set.all() -> will give you a list of all filter details.
    FilterDetail models contain actual filter value and process status (see website/choices.py)
    for pfilter in filters: -> each pfilter will be the filter model.
    Filter models contain data types and the name of the filter
    '''
    def __init__(self, selector, backend_id, dry_run, verbosity):
        self.selector = selector
        self.backend_id = backend_id
        self.dry_run = dry_run
        self.verbosity = verbosity

    def done(self):
        if self.dry_run:
            return

    def all_filters(self):
        return self.selector.filterdetail_set.filter(pfilter__associated_backend=self.backend_id)

    def filters(self):
        return self.all_filters().exclude(status=PROCESSED)

    def filter_start(self, flter):
        if not self.dry_run:
            flter.status = ONGOING
            flter.save()

    def filter_done(self, flter):
        if not self.dry_run:
            flter.status = PROCESSED
            flter.save()

    def debug(self):
        self.run()

    def run(self):
        raise NotImplementedError('filter runners must override the run() method')

    def discovered_project(self, url):
        if self.verbosity >= 3:
            print("discovered project: " + url)
        if self.dry_run:
            return

        p, _ = Project.objects.get_or_create(dataset=self.selector.input_dataset, url=url)
        if p.snapshots.exists():
            s = p.snapshots.order_by('-datetime_processed').first()
        else:
            s, _ = ProjectSnapshot.objects.get_or_create(project=p)
        Selection.objects.get_or_create(project_selector=self.selector, snapshot=s)
