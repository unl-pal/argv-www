from website.models import ProjectSelector, Project, Selection

class Runner:
    '''Notes on attributes from selector
    
    filters = selector.filterdetail_set.all() -> will give you a list of all filter details.
    FilterDetail models contain actual filter value and process status (Ready, Ongoing, Processed)
    for pfilter in filters: -> each pfilter will be the filter model.
    Filter models contain data types and the name of the filter
    '''
    def __init__(self, selector):
        self.selector = selector
    
    def done(self):
        self.selector.processed = 'PROCESSED'
        for flter in self.selector.filterdetail_set.all():
            if flter.status == 'READY':
                self.selector.processed = 'ONGOING'
        self.selector.save()
    
    def filter_done(self, flter):
        flter.status = 'PROCESSED'
        flter.save()
        
    def run(self):
        # Sends to backend
        print('Sending to backend...')
    
    def save_results(self, url):
        for selection in self.selector.project_set.all():
            if selection.project.url == url:
                return
        new_project = Project.create(dataset=self.selector.dataset, url=url)
        new_project.save()
        new_selection = Selection.create(project_selector=self.selector, project=new_project)
        new_selection.save()
