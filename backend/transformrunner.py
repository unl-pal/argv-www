class TransformRunner:
    def __init__(self, transformed_project, backend_id, dry_run, verbosity):
        self.transformed_project = transformed_project
        self.backend_id = backend_id
        self.dry_run = dry_run
        self.verbosity = verbosity

    def done(self):
        pass

    def run(self):
        raise NotImplementedError('filter runners must override the run() method')
