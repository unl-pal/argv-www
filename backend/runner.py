from website.models import ProjectSelector

class Runner:
    '''Notes on attributes from selector
    
    filters = selector.filterdetail_set.all() -> will give you a list of all filter details.
    FilterDetail models contain actual filter value and process status (Ready, Ongoing, Processed)
    for pfilter in filters: -> each pfilter will be the filter model.
    Filter models contain data types and the name of the filter
    '''
    def __init__(self, selector):
        self.selector = selector
        
    def run(self):
        # Sends to backend
        print('Sending to backend...')
    
    def save_results(self):
        # Saves list of urls and returns a list of urls
        print('Saving results')
