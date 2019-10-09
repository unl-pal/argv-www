import time
from backend.filterrunner import FilterRunner
from boaapi.boa_client import BoaClient
from decouple import config

from website.choices import *

class FilterRunner(FilterRunner):
    template_start = """o: output collection of string;
filtered := false;

"""

    template_end = """if (!filtered)
    o << input.project_url;"""

    def translate_filter(self, filtr):
        s = "if (!filtered) {\n";
        if filtr.pfilter.pk == 1:
            s += "    if (len(input.code_repositories) < 1 || len(input.code_repositories[0].revisions) < " + str(filtr.value) + ")\n" + "        filtered = true;\n"
        elif filtr.pfilter.pk == 2:
            s += """    file_count := 0;
    visit(input, visitor {
        before node: CodeRepository -> {
            snapshot := getsnapshot(node);
            foreach (i: int; def(snapshot[i]))
                visit(snapshot[i]);
            stop;
        }
        before n: ChangedFile ->
            if (iskind("SOURCE_", n.kind))
                file_count = file_count + 1;
    });
    if (file_count < """ + str(filtr.value) + """)
        filtered = true;
"""
        s += "}\n\n"
        return s

    def run(self):
        if self.verbosity >= 1:
            print('        -> boa backend processing: ' + self.selector.slug)

        query = '# ' + self.selector.slug + '\n' + self.template_start
        for f in self.filters():
            query += self.translate_filter(f)
        query += self.template_end
        if self.verbosity >= 3:
            print(query)

        client = BoaClient()
        client.login(config('BOA_USER'), config('BOA_PW'))

        job = client.query(query, client.get_dataset('2015 September/GitHub'))
        if self.verbosity >= 2:
            print('            -> boa job: http://boa.cs.iastate.edu/boa/index.php?q=boa/job/' + str(job.id))

        for f in self.filters():
            self.filter_start(f)

        while job.compiler_status == 'Running' or job.exec_status == 'Running' or job.compiler_status == 'Waiting' or job.exec_status == 'Waiting':
            job.refresh()
            time.sleep(3)

        if job.compiler_status == 'Error':
            print('job ' + str(job.id) + ' had compile error')
        elif job.exec_status == 'Error':
            print('job ' + str(job.id) + ' had exec error')
        else:
            output = job.output().decode('utf-8')

            for line in output.splitlines(False):
                self.save_result(line[6:])

            for f in self.filters():
                self.filter_done(f)

            self.done()

        client.close()
