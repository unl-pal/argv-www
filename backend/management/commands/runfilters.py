import socket
import time
import traceback

from django.core.management.base import BaseCommand
from django.utils import timezone

from website.choices import PROCESSED
from website.models import FilterDetail, Selection


'''Run Filter Poller

Usage: manage.py runfilters
Runs a poller in the background to run filters on cloned repos
'''
class Command(BaseCommand):
    help = 'Runs a poller in the background to run filters on cloned repos'
    POLL_INTERVAL = 3

    def add_arguments(self, parser):
        parser.add_argument('--debug', help='Debug filtering (implies --dry-run --no-poll -v 3)', action='store_true')
        parser.add_argument('--dry-run', help='Perform a dry-run (don\'t change the database)', action='store_true')
        parser.add_argument('--no-poll', help='Perform one round of processing instead of polling', action='store_true')

    def handle(self, *args, **options):
        self.debug = options['debug']
        self.dry_run = options['dry_run'] or self.debug
        self.no_poll = options['no_poll'] or self.debug
        self.verbosity = 3 if self.debug else options['verbosity']

        self.host = socket.gethostname()

        while True:
            for selection in Selection.objects.filter(retained__isnull=True).filter(snapshot__host=self.host)[:10]:
                if selection:
                    try:
                        self.process_selection(selection)
                    except:
                        self.stdout.write('error processing: ' + str(selection))
                        traceback.print_exc()

            if self.no_poll:
                break
            time.sleep(self.POLL_INTERVAL)

    def process_selection(self, selection):
        self.stdout.write('processing Selection: ' + str(selection))

        if selection.snapshot.path and self.test_repo(selection):
            self.retained_selection(selection)
        else:
            self.filtered_selection(selection)

        if not Selection.objects.filter(project_selector=selection.project_selector).filter(retained__isnull=True).exists():
            if self.verbosity >= 3:
                self.stdout.write("-> ProjectSelector finished: " + str(selection.project_selector))

            if not self.dry_run:
                selection.project_selector.status = PROCESSED
                selection.project_selector.fin_process = timezone.now()
                selection.project_selector.save(update_fields=['status', 'fin_process', ])

    def test_repo(self, selection):
        project_selector = selection.project_selector
        snapshot = selection.snapshot

        for f in FilterDetail.objects.filter(project_selector=project_selector).all():
            value = f.value
            filter = f.pfilter.flter.name
            if filter == "Minimum number of commits":
                if not snapshot.commits or snapshot.commits < int(value):
                    return False
            elif filter == "Maximum number of commits":
                if not snapshot.commits or snapshot.commits > int(value):
                    return False
            elif filter == "Minimum number of source files":
                if not snapshot.src_files or snapshot.src_files < int(value):
                    return False
            elif filter == "Maximum number of source files":
                if not snapshot.src_files or snapshot.src_files > int(value):
                    return False
            elif filter == "Minimum number of committers":
                if not snapshot.committers or snapshot.committers < int(value):
                    return False
            elif filter == "Maximum number of committers":
                if not snapshot.committers or snapshot.committers > int(value):
                    return False
            elif filter == "Minimum number of stars":
                pass
            elif filter == "Maximum number of stars":
                pass

        return True

    def retained_selection(self, selection):
        if self.verbosity >= 3:
            self.stdout.write("-> retained snapshot: " + str(selection.snapshot))
        if self.dry_run:
            return

        selection.retained = True
        selection.save()

    def filtered_selection(self, selection):
        if self.verbosity >= 3:
            self.stdout.write("-> filtered snapshot: " + str(selection.snapshot))
        if self.dry_run:
            return

        selection.retained = False
        selection.save()
