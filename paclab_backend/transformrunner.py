import os
import shutil
import subprocess
import traceback

from decouple import config

from backend.transformrunner import TransformRunner
from website.models import TransformedProject


class TransformRunner(TransformRunner):
    def run(self):
        self.transformer_path = config('PACLAB_TRANSFORM_PATH')

        self.generate_config()

        for project in self.projects():
            try:
                self.transform_project(project, isinstance(project, TransformedProject))
            except:
                print('error transforming: ' + project.path)
                traceback.print_exc()

        self.done()

    def generate_config(self):
        pass

    def transform_project(self, project, istransform):
        proj_path = project.path
        project_name = proj_path[proj_path.index("/") + 1:]

        filter_path = os.path.join('/tmp', project_name + '-filter')
        in_path = os.path.join(self.repo_path, proj_path)
        path = os.path.join(str(self.transformed_project.pk), proj_path)
        out_path = os.path.join(self.transformed_path, path)

        if os.path.exists(out_path):
            print('    -> SKIPPING: already exists: ' + project_name)
            self.finish_project(project, istransform, out_path)
            return

        if os.path.exists(filter_path):
            shutil.rmtree(filter_path)

        print(['./run.sh', in_path, filter_path, out_path])
        proc = subprocess.Popen(['./run.sh', in_path, filter_path, out_path],
            cwd=self.transformer_path,
            stdout=subprocess.PIPE if self.verbosity < 2 else None,
            stderr=subprocess.PIPE if self.verbosity < 2 else None)
        if self.verbosity >= 2:
            print('    -> process id: ' + str(proc.pid))
        proc.wait()

        if proc.returncode == 0:
            self.finish_project(project, istransform, path)
        else:
            self.finish_project(project, istransform)

        if os.path.exists(filter_path):
            shutil.rmtree(filter_path)
