from website.models import ProjectSelector, RepositoryURL

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
        [self.selector.processed = 'ONGOING' for flter in self.selector.filterdetail_set.all() if flter.status == 'READY']
        self.selector.save()
    
    def filter_done(self, flter):
        flter.status = 'PROCESSED'
        flter.save()
        
    def run(self):
        # Sends to backend
        print('Sending to backend...')
    
    def save_results(self, url):
        for repourl in self.selector.project.urls_set.all():
            current_url = repourl.url
            if url == current_url:
                return
        new_url = RespositoryURL.create(project=self.selector.project, url=url)
        new_url.save()
