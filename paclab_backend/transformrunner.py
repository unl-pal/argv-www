import os
import shutil
import subprocess
import tempfile
import traceback

from decouple import config

from backend.transformrunner import TransformRunner as TR
from website.models import TransformedProject, TransformParameterValue


"""
Storage Layout

/TRANSFORMED_PATH
    /selector/ProjectSnapshot.pk
        /TransformOption.pk
            ...
        /TransformOption.pk
            ...
    /transform/TransformedProject.pk
        /TransformOption.pk
            ...
        /TransformOption.pk
            ...
"""
class TransformRunner(TR):
    def run(self):
        self.transformer_path = config('PACLAB_TRANSFORM_PATH')
        self.config_path = os.path.join(tempfile.gettempdir(), 'paclab-' + str(self.transformed_project.pk) + '-config.properties')
        print(self.config_path)
        self.generate_config()

        for project in self.remaining_projects():
            try:
                self.transform_project(project, isinstance(project, TransformedProject))
            except:
                print('error transforming: ' + project.path)
                traceback.print_exc()

        if os.path.exists(self.config_path):
            os.remove(self.config_path)

        self.done()

    def generate_config(self):
        props = {
            'csv': 'dataset.csv',
            'projectCount': '50',
            'maxLoc': '10000',
            'minLoc': '100',
            'downloadDir': 'database',
            'benchmarkDir': 'benchmarks',
            'debugLevel': '2',
            'type': 'I',
            'minExpr': '3',
            'minIfStmt': '1',
            'minParams': '1',
            'target': 'DEF',
        }

        for p in TransformParameterValue.objects.filter(option=self.transformed_project.transform):
            props[p.parameter.name] = p.value

        with open(self.config_path, 'w') as config:
            for p in props.items():
                config.write(p[0] + '=' + p[1] + '\n')

    def transform_project(self, project, istransform):
        proj_path = project.path
        project_name = proj_path[proj_path.index("/") + 1:]

        # input path - the data to transform
        in_path = os.path.join(self.transformed_path if istransform else self.repo_path, proj_path)

        # since there are 2 phases, we need a temporary path
        tmp_path = os.path.join(tempfile.gettempdir(), project_name + '-' + str(self.transform.pk) + '-filter')

        # output path - where to store the result
        path = os.path.join('transform' if istransform else 'selector', str(project.pk), str(self.transform.pk))
        out_path = os.path.join(self.transformed_path, path)

        exists = True

        if istransform:
            exists = TransformedProject.objects.filter(transform=self.transform, src_transform=project).exists()
        else:
            exists = TransformedProject.objects.filter(transform=self.transform, src_project=project).exists()

        if not os.path.exists(out_path):
            exists = False

        if exists:
            print('    -> SKIPPING: already exists: ' + project_name)
            self.finish_project(project, istransform, path)
            return

        if os.path.exists(tmp_path):
            shutil.rmtree(tmp_path)

        if self.verbosity >= 2:
            print(['./run.sh', in_path, tmp_path, out_path])
        proc = subprocess.Popen(['./run.sh', in_path, tmp_path, out_path],
                                cwd=self.transformer_path,
                                stdout=subprocess.PIPE if self.verbosity >= 2 else None,
                                stderr=subprocess.PIPE if self.verbosity >= 2 else None)

        try:
            if self.verbosity >= 3:
                print('    -> process id: ' + str(proc.pid))
            proc.wait(5 * 60)
        except subprocess.TimeoutExpired:
            pass

        if proc.returncode == 0:
            self.finish_project(project, istransform, path)
        else:
            os.makedirs(out_path, 0o755, True)
            self.finish_project(project, istransform)

        if os.path.exists(tmp_path):
            shutil.rmtree(tmp_path)
