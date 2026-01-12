#!/usr/bin/env python
# coding: utf-8

from argv.celery import app
from celery.utils.log import get_task_logger
celery_logger = get_task_logger(__name__)

from django.db import transaction
from django.utils import timezone

from decouple import config

from website.choices import READY, ONGOING, PROCESSED

from website.models import Selection, FilterDetail, ProjectSelector


FILTERER_DRY_RUN = config('FILTERER_DRY_RUN', default=False, cast=bool)

__all__ = ['filter_selection']

@app.task(bind=True, retry_backoff=True)
def filter_selection(self, selection_pk):
    selection = Selection.objects.get(pk=selection_pk)
    if selection.snapshot.host is None:
        raise self.retry()

    celery_logger.info(f' --->>>> processing Selection {selection}')

    if selection.snapshot.path and test_repo(selection):
        celery_logger.debug(f'-> retained snapshot: {selection.snapshot}')
        selection.retained = True
        selection.save()
    else:
        celery_logger.debug(f'-> filtered snapshot: {selection.snapshot}')
        selection.retained = False
        selection.save()

    selection.status = PROCESSED
    selection.save(update_fields=['status'])

    with transaction.atomic():
        if not Selection.objects.filter(selector=selection.project_selector).exclude(status=PROCESSED).exists():
            celery_logger.debug(f'ProjectSelector finished: {selection.project_selector}')
            selection.project_selector.status = PROCESSED
            selection.project_selector.fin_process = timezone.now()
            selection.project_selector.save(update=['status', 'fin_process',])


def test_repo(selection):
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

