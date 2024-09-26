#!/usr/bin/env python
# coding: utf-8

from argv.celery import app

from celery.utils.log import get_task_logger
logger = get_task_logger(__name__)

from django.conf import settings
from django.utils import timezone

import socket, os, shutil, subprocess, tempfile, time, traceback

from os import walk

from website.choices import READY, ONGOING, PROCESSED
from website.models import Dataset, Project, ProjectSnapshot, Selection, FilterDetail, ProjectSelector

### Utils

# TODO: Verbosity fix
def run_command(args, cwd):
    p = subprocess.Popen(args,
                         cwd=cwd,
                         stdout=None,
                         stderr=None
                         # stdout=subprocess.PIPE if self.verbosity < 3 else None,
                         # stderr=subprocess.PIPE if self.verbosity < 3 else None
                         )
    p.wait()
    return p

### Cloner Tasks

CLONER_DRY_RUN = getattr(settings, 'CLONER_DRY_RUN', False)
CLONER_NO_UNPACK = getattr(settings, 'CLONER_NO_UNPACK', False)
CLONER_SHALLOW = getattr(settings, 'CLONER_SHALLOW', False)
CLONER_NOFILTER = getattr(settings, 'CLONER_NOFILTER', False)

@app.task
def process_snapshot(snapshot_pk):
    logger.info(f"Running for {snapshot_pk}")
    snapshot = ProjectSnapshot.objects.get(pk=snapshot_pk)

    logger.info('processing ProjectSnapshot: ' + snapshot.project.url)
    if not CLONER_DRY_RUN:
        snapshot.host = socket.gethostname()
        snapshot.save()

    host = snapshot.project.url[snapshot.project.url.index('://') + 3:]
    project_name = host[host.index('/') + 1:]
    project_base = project_name[0:project_name.index('/')] if '/' in project_name else project_name
    project_parent = project_name[0:project_name.rindex('/')] if '/' in project_name else ''
    host = host[:host.index('/')]

    repo_root = os.path.join(getattr(settings, 'REPO_PATH'), host)
    path = os.path.join(repo_root, project_name)
    tmp = tempfile.gettempdir()
    src_dir = os.path.join(tmp, project_name)
    git_dir = os.path.join(src_dir, '.git')

    logger.debug('    -> root:    ' + repo_root)
    logger.debug('    -> name:    ' + project_name)
    logger.debug('    -> base:    ' + project_base)
    logger.debug('    -> path:    ' + path)
    logger.debug('    -> tmp:     ' + tmp)
    logger.debug('    -> src_dir: ' + src_dir)
    logger.debug('    -> git_dir: ' + git_dir)

    if os.path.exists(path):
        logger.info('    -> SKIPPING: already exists: ' + project_name)
        if not CLONER_DRY_RUN:
            snapshot.path = os.path.join(host, project_name)
            snapshot.datetime_processed = timezone.now()
            snapshot.save()
            return

    try:
        clone_repo(host, src_dir, project_name, git_dir, repo_root, project_parent, snapshot)
    finally:
        # cleanup temp files
        if os.path.exists(os.path.join(tmp, project_base)):
            shutil.rmtree(os.path.join(tmp, project_base))

        if not CLONER_DRY_RUN:
            snapshot.datetime_processed = timezone.now()
            snapshot.save()

def clone_repo(host, src_dir, project_name, git_dir, repo_root, project_parent, snapshot):
    # clone and filter the repo
    os.makedirs(src_dir, 0o755, True)
    run_command(['git', 'init'], src_dir)

    run_command(['git', 'config', '--local', 'core.sparseCheckout', 'true'], src_dir)
    with open(os.path.join(src_dir, '.git/info/sparse-checkout'), 'w') as sparseFile:
        sparseFile.writelines('**/*.java\n')

    # get all remote objects
    run_command(['git', 'remote', 'add', 'origin', 'https://foo:bar@' + host + '/' + project_name], src_dir)
    if CLONER_SHALLOW:
        p = run_command(['git', 'fetch', '--depth', '1'], src_dir)
    else:
        p = run_command(['git', 'fetch'], src_dir)

    if p.returncode == 0:
        # filter out unwanted objects
        if not CLONER_NOFILTER:
            run_command(['git', 'filter-repo', '--path-regex', '[^/]+\\.java$'], src_dir)
            run_command(['git', 'remote', 'add', 'origin', 'https://foo:bar@' + host + '/' + project_name], src_dir)

        # unpack object files
        unpack_git(git_dir)

        # checkout working tree
        # similar to: git remote show origin | grep "HEAD branch" | cut -d ":" -f 2
        masterrefproc = subprocess.Popen(['git', 'remote', 'show', 'origin'], cwd=src_dir, stdout=subprocess.PIPE, stderr=None)
        parts = filter(lambda x: 'HEAD branch' in x, masterrefproc.stdout.read().decode().strip().split('\n'))
        masterref = list(parts)[0].split(':')[1].strip()
        p = run_command(['git', 'checkout', masterref], src_dir)

        if not CLONER_DRY_RUN:
            if p.returncode == 0:
                if not os.path.exists(repo_root):
                    logger.info('root does not exist, creating: ' + repo_root)
                    os.makedirs(repo_root, 0o755, True)

                update_metrics(snapshot, src_dir)

                # move repo to final location
                parent_dir = os.path.join(repo_root, project_parent)
                logger.info('moving ' + src_dir + ' -> ' + parent_dir)
                if not os.path.exists(parent_dir):
                    os.makedirs(parent_dir, 0o755, True)
                    shutil.move(src_dir, parent_dir)

                snapshot.path = os.path.join(host, project_name)
            else:
                logger.info('failed to clone ' + project_name)

def unpack_git(git_dir):
    """unpack all Git object files"""
    if CLONER_NO_UNPACK:
        return

    if os.path.exists(os.path.join(git_dir, 'objects/pack')):
        packdir = os.path.join(git_dir, 'pack')
        shutil.move(os.path.join(git_dir, 'objects/pack'), packdir)

        for (root, dirs, files) in walk(packdir):
            for f in files:
                if f.endswith('.pack'):
                    with open(os.path.join(root, f), 'rb') as packfile:
                        unpack = subprocess.Popen(['git', 'unpack-objects'], cwd=git_dir, stdin=subprocess.PIPE, stdout=None, stderr=None)
                        while True:
                            b = packfile.read(64 * 1024)
                            if not b:
                                break
                            unpack.stdin.write(b)

        shutil.rmtree(packdir)

def update_metrics(project, path):
    # update number of commits
    p = subprocess.Popen(['git', 'rev-list', '--count', 'HEAD'], cwd=path, stdout=subprocess.PIPE, stderr=None)
    project.commits = int(p.stdout.read().decode().strip())
    logger.info(path + ', commits =' + str(project.commits))

    # update number of committers
    p = subprocess.Popen(['git', 'shortlog', '-sne'], cwd=path, stdout=subprocess.PIPE, stderr=None)
    project.committers = p.stdout.read().decode().count('\n')
    logger.info(path + ', committers =' + str(project.committers))

    # update src file counts
    # similar to: find . -name '*.java' | wc -l
    p = subprocess.Popen(['find', '.', '-name', '*.java'], cwd=path, stdout=subprocess.PIPE, stderr=None)
    project.src_files = p.stdout.read().decode().count('\n')
    logger.info(path + ', src_files =' + str(project.src_files))

### Filterer Task(s)

FILTERER_DRY_RUN = getattr(settings, 'FILTERER_DRY_RUN', False)

@app.on_after_finalize.connect
def add_periodic_tasks(sender, **kwargs):
    sender.add_periodic_task(5, spawn_filterers.s())
    sender.add_periodic_task(5, cleanup_filterers.s())

@app.task
def spawn_filterers():
    for selection in Selection.objects.filter(status=READY):
        if selection.snapshot.host is not None:
            selection.status = ONGOING
            selection.save(update_fields=['status',])
            filter_selection.delay(selection.pk)

@app.task
def cleanup_filterers():
    for selector in ProjectSelector.objects.exclude(status=PROCESSED):
        if not Selection.objects.filter(project_selector=selector).exclude(status=PROCESSED).exists():
            logger.debug(f'-> ProjectSelector finished: {selector}')
            selector.status = PROCESSED
            selector.fin_process = timezone.now()
            selector.save(update_fields=['status', 'fin_process', ])

@app.task
def filter_selection(selection_pk):
    selection = Selection.objects.get(pk=selection_pk)

    logger.info(f'processing Selection {selection}')

    if selection.snapshot.path and test_repo(selection):
        logger.debug(f'-> retained snapshot: {selection.snapshot}')
        selection.retained = True
        selection.save()
    else:
        logger.debug(f'-> filtered snapshot: {selection.snapshot}')
        selection.retained = False
        selection.save()

    selection.status = PROCESSED
    selection.save(update_fields=['status'])

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
