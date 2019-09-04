from website.models import ProjectSelector

class Runner:
    def __init__(self, selector):
        self.selector = selector
        
    def run(self):
        # Sends to backend
        print('Sending to backend...')

    def transform(self):
        # transforms data, getting it ready for api
        print('Transforming selector into query...')
    
    def save_results(self):
        # Saves list of urls and returns a list of urls
        print('Saving results')
