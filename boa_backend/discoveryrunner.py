import time

from boaapi.boa_client import BoaClient
from boaapi.status import CompilerStatus, ExecutionStatus
from decouple import config

from backend.discoveryrunner import DiscoveryRunner as DR


class DiscoveryRunner(DR):
    template_start = """o: output collection of string;

filtered := true;
snapshot: array of ChangedFile;

# ensure there is at least 1 source file in the snapshot
visit(input, visitor {
    before node: CodeRepository -> {
        snapshot = getsnapshot(node, "SOURCE_");
        if (len(snapshot) > 0)
            filtered = false;
        stop;
    }
});

"""

    template_end = """if (!filtered)
    o << input.project_url;"""

    def translate_filter(self, filtr):
        s = 'if (!filtered) {\n'

        #
        if filtr.pfilter.flter.name == 'Minimum number of commits':
            s += """    if (len(input.code_repositories) < 1 || len(input.code_repositories[0].revisions) < {})
        filtered = true;
""".format(filtr.value)

        #
        elif filtr.pfilter.flter.name == 'Maximum number of commits':
            s += """    if (len(input.code_repositories) < 1 || len(input.code_repositories[0].revisions) > {})
        filtered = true;
""".format(filtr.value)

        #
        elif filtr.pfilter.flter.name == 'Minimum number of source files':
            s += """    min_file_count := 0;
    foreach (i: int; def(snapshot[i]))
        if (iskind("SOURCE_", snapshot[i].kind))
            min_file_count = min_file_count + 1;
    if (min_file_count < {})
        filtered = true;
""".format(filtr.value)

        #
        elif filtr.pfilter.flter.name == 'Maximum number of source files':
            s += """    max_file_count := 0;
    foreach (i: int; def(snapshot[i]))
        if (iskind("SOURCE_", snapshot[i].kind))
            max_file_count = max_file_count + 1;
    if (max_file_count > {})
        filtered = true;
""".format(filtr.value)

        #
        elif filtr.pfilter.flter.name == 'Minimum number of committers':
            s += """    min_committers: set of string;
    visit(input, visitor {{
        before n: Revision -> add(min_committers, n.committer.username);
    }});
    if (len(min_committers) < {})
        filtered = true;
""".format(filtr.value)

        #
        elif filtr.pfilter.flter.name == 'Maximum number of committers':
            s += """    max_committers: set of string;
    visit(input, visitor {{
        before n: Revision -> add(max_committers, n.committer.username);
    }});
    if (len(max_committers) > {})
        filtered = true;
""".format(filtr.value)
        # failsafe
        else:
            return ''

        return s + '}\n\n'

    def build_query(self, flters):
        query = '# ARG-V project selection\n' + self.template_start
        for f in flters:
            query += self.translate_filter(f)
        return query + self.template_end

    def run(self):
        if self.verbosity >= 1:
            print('        -> boa backend processing: ' + self.selector.slug)

        query = self.build_query(self.all_filters())
        if self.verbosity >= 3:
            print(query)

        client = BoaClient()
        client.login(config('BOA_USER'), config('BOA_PW'))

        job = client.query(query, client.get_dataset('2019 October/GitHub'))
        if self.verbosity >= 2:
            print('            -> boa job: http://boa.cs.iastate.edu/boa/index.php?q=boa/job/' + str(job.id))

        for f in self.filters():
            self.filter_start(f)

        job.wait()
        if self.verbosity >= 3:
            print(f'           -> boa job {job.id} completed')

        if job.compiler_status == CompilerStatus.ERROR:
            print('job ' + str(job.id) + ' had compile error')
        elif job.exec_status == ExecutionStatus.ERROR:
            print('job ' + str(job.id) + ' had exec error')
        else:
            try:
                output = job.output()

                for line in output.splitlines(False):
                    self.discovered_project(line[6:])

                if self.verbosity >= 3:
                    print("            -> finished processing boa job")
            except:
                pass

            self.done()

        client.close()

    def debug(self):
        print(self.build_query(self.all_filters()))
