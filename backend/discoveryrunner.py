from website.choices import ONGOING, PROCESSED
from website.models import Project, ProjectSnapshot, Selection


class DiscoveryRunner:
    def __init__(self, selector, backend_id, dry_run, verbosity):
        self.selector = selector
        self.backend_id = backend_id
        self.dry_run = dry_run
        self.verbosity = verbosity

    def done(self):
        for f in self.filters():
            self.filter_done(f)

    def all_filters(self):
        return self.selector.filterdetail_set.filter(pfilter__backend=self.backend_id)

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
        raise NotImplementedError('discovery runners must override the run() method')

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
