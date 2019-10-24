import subprocess

from backend.transformrunner import TransformRunner
from decouple import config

class TransformRunner(TransformRunner):
    def run(self):
        self.repo_path = config('REPO_PATH')
        self.filtered_path = config('TRANSFORMED_PATH') + '-tmp'
        self.transformed_path = config('TRANSFORMED_PATH')
        self.transformer_path = config('PACLAB_TRANSFORM_PATH')

        for project in self.projects():
            self.transform_project(project)

        self.done()

    def transform_project(self, project):
        host = project.url[project.url.index("://") + 3:]
        project_name = host[host.index("/") + 1:]
        host = host[:host.index("/")]

        repo_root = self.repo_path
        if repo_root[-1:] != '/':
            repo_root += '/'
        repo_root += host
        path = repo_root + '/' + project_name

        filter_root = self.filtered_path
        if filter_root[-1:] != '/':
            filter_root += '/'
        filter_root += host
        filter_path = filter_root + '/' + project_name

        transformed_root = self.transformed_path
        if transformed_root[-1:] != '/':
            transformed_root += '/'
        transformed_root += host
        out_path = transformed_root + '/' + project_name

        proc = subprocess.Popen(['./run.sh', path, filter_path, out_path], cwd=self.transformer_path, stdout=subprocess.PIPE if self.verbosity < 2 else None, stderr=subprocess.PIPE if self.verbosity < 2 else None)
        if self.verbosity >= 2:
            self.stdout.write('    -> process id: ' + str(proc.pid))
        proc.wait()

        if not self.dry_run:
            if proc.returncode == 0:
                self.finish_project(project, out_path)
            else:
                self.finish_project(project)
