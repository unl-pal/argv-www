import os
import shutil
import subprocess

from django.conf import settings

from backend.transformrunner import TransformRunner


class TransformRunner(TransformRunner):
    def run(self):
        self.repo_path = getattr(settings, 'REPO_PATH')
        self.transformed_path = getattr(settings, 'TRANSFORMED_PATH')
        self.transformer_path = getattr(settings, 'PACLAB_TRANSFORM_PATH')

        for project in self.projects():
            try:
                self.transform_project(project)
            except:
                print('error transforming: ' + project.path)

        self.done()

    def generate_config(self):
        pass

    def transform_project(self, project):
        host = project.url[project.url.index("://") + 3:]
        project_name = host[host.index("/") + 1:]
        host = host[:host.index("/")]

        repo_root = self.repo_path
        if repo_root[-1:] != '/':
            repo_root += '/'
        repo_root += host
        path = repo_root + '/' + project_name

        filter_path = '/tmp/' + project_name + '-filter'

        transformed_root = self.transformed_path
        if transformed_root[-1:] != '/':
            transformed_root += '/'
        transformed_root += host
        out_path = transformed_root + '/' + project_name

        if os.path.exists(out_path):
            print('    -> SKIPPING: already exists: ' + project_name)
            self.finish_project(project, out_path)
            return

        if os.path.exists(filter_path):
            shutil.rmtree(filter_path)

        proc = subprocess.Popen(['./run.sh', path, filter_path, out_path], cwd=self.transformer_path, stdout=subprocess.PIPE if self.verbosity < 2 else None, stderr=subprocess.PIPE if self.verbosity < 2 else None)
        if self.verbosity >= 2:
            print('    -> process id: ' + str(proc.pid))
        proc.wait()

        if not self.dry_run:
            if proc.returncode == 0:
                self.finish_project(project, host + '/' + project_name)
            else:
                self.finish_project(project)

        if os.path.exists(filter_path):
            shutil.rmtree(filter_path)
