class Runner:
    def __init__(self, name, val_type, value):
        self.name = name
        self.val_type = val_type
        self.value = value
    
    def run(self):
        # Sends to backend
        print('Sending to backend...')

    def transform(self):
        # transforms data, getting it ready for api
        print(self.name)
        print(self.val_type)
        print(self.value)
