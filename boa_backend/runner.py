import time
from backend.runner import Runner
from boaapi.boa_client import BoaClient

from website.choices import *

class Runner(Runner):
    template_start = """o: output collection of string;
filtered := false;

"""

    template_end = """
if (!filtered)
    o << input.project_url;"""

    def translate_filter(self, filtr):
        if filtr.pfilter.pk == 1:
            return "if (!filtered && (len(input.code_repositories) < 1 || len(input.code_repositories[0].revisions) < " + str(filtr.value) + "))\n" + "    filtered = true;\n\n"
        elif filtr.pfilter.pk == 2:
            return """if (!filtered) {
    file_count := 0;
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
}
"""
        return ""

    def all_filters(self):
        return self.selector.filterdetail_set.all()

    def filters(self):
        return self.selector.filterdetail_set.filter(status=READY)

    def run(self):
        print('        -> boa backend processing: ' + self.selector.slug)

        query = self.template_start
        for f in self.filters():
            query += self.translate_filter(f)
        query += self.template_end
        print(query)

        client = BoaClient()
        client.login('TODO USERNAME', 'TODO PASSWORD')
        job = client.query(query, client.get_dataset('2015 September/GitHub'))
        while job.compiler_status == 'Running' or job.exec_status == 'Running' or job.compiler_status == 'Waiting' or job.exec_status == 'Waiting':
            job.refresh()
            time.sleep(3)

        if job.compiler_status == 'Error' or job.exec_status == 'Error':
            print('job ' + str(job.id) + 'had ERROR')
        else:
            output = job.output()

            for line in output.splitlines(False):
                self.save_result(str(line)[8:])

            for f in self.filters():
                self.filter_done(f)

            self.done()

        client.close()
