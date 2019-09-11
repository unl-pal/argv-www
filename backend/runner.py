from website.models import ProjectSelector, Project, Selection
from website.choices import *

class Runner:
    '''Notes on attributes from selector
    
    filters = selector.filterdetail_set.all() -> will give you a list of all filter details.
    FilterDetail models contain actual filter value and process status (Ready, Ongoing, Processed)
    for pfilter in filters: -> each pfilter will be the filter model.
    Filter models contain data types and the name of the filter
    '''
    def __init__(self, selector, dry_run):
        self.selector = selector
        self.dry_run = dry_run
    
    def done(self):
        filters_not_done = self.selector.filterdetail_set.exclude(status=PROCESSED)
        if not self.dry_run and len(filters_not_done) == 0:
            self.selector.processed = PROCESSED
            self.selector.save()
    
    def filter_done(self, flter):
        if not self.dry_run:
            flter.status = PROCESSED
            flter.save()
        
    def run(self):
        raise NotImplementedError('runners must override the run() method')
    
    def save_result(self, url):
        if self.dry_run:
            return

        for selection in self.selector.selection_set.all():
            if selection.project.url == url:
                return

        new_project = Project.objects.create(dataset=self.selector.input_dataset, url=url)
        new_project.save()
        new_selection = Selection.objects.create(project_selector=self.selector, project=new_project)
        new_selection.save()
