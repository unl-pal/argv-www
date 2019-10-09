class TransformRunner:
    def __init__(self, transformed_project, backend_id, dry_run, verbosity):
        self.transformed_project = transformed_project
        self.backend_id = backend_id
        self.dry_run = dry_run
        self.verbosity = verbosity

    def done(self):
        if self.dry_run:
            return
        if not self.transformed_project.transforms_set.exclude(status=PROCESSED).exists():
            self.transformed_project.status = PROCESSED
            self.transformed_project.save()

    def run(self):
        raise NotImplementedError('filter runners must override the run() method')
