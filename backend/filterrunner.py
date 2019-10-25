from django.utils import timezone
from website.models import ProjectSelector, Project, Selection
from website.choices import *

class FilterRunner:
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
        if not self.selector.filterdetail_set.exclude(status=PROCESSED).exists():
            self.selector.status = PROCESSED
            self.selector.fin_process = timezone.now()
            self.selector.save()

            # handle case where filters were too restrictive and nothing matched
            if self.selector.project.count() == 0:
                for t in self.selector.projecttransformer_set.all():
                    t.status = PROCESSED
                    t.datetime_processed = timezone.now()
                    t.save()

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

    def run(self):
        raise NotImplementedError('filter runners must override the run() method')

    def save_result(self, url):
        if self.verbosity >= 3:
            print("saving result: " + url)
        if self.dry_run:
            return

        project, created = Project.objects.get_or_create(dataset=self.selector.input_dataset, url=url)
        Selection.objects.get_or_create(project_selector=self.selector, project=project)
