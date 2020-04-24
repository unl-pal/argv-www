from django.utils import timezone

from website.choices import *
from website.models import Project, ProjectSelector, Selection


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
        if not self.filters().exists():
            self.selector.status = PROCESSED
            self.selector.fin_process = timezone.now()
            self.selector.save()

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
        Selection.objects.get_or_create(project_selector=self.selector, project=p)

    def test_project(self, path):
        return True

    def try_retain_project(self, p):
        s = selector.project_set.get(p)
        if self.test_project(p.path):
            self.retained_project(p.url, s)
        else:
            self.filtered_project(p.url, s)

    def retained_project(self, url, s):
        if self.verbosity >= 3:
            print("-> retained project: " + url)
        if self.dry_run:
            return

        s.retained = True
        s.save()

    def filtered_project(self, url, s):
        if self.verbosity >= 3:
            print("-> filtered project: " + url)
        if self.dry_run:
            return

        s.retained = False
        s.save()
