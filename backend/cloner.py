#!/usr/bin/env python
# coding: utf-8

from argv.celery import app
from celery.exceptions import SoftTimeLimitExceeded
from celery.utils.log import get_task_logger
celery_logger = get_task_logger(__name__)

from django.conf import settings
from django.utils import timezone

from decouple import config

import subprocess
import shutil
import os
import tempfile
import socket

from website.models import ProjectSnapshot

from os import walk

import backend.tasks

CLONER_DRY_RUN = config('CLONER_DRY_RUN', default=False, cast=bool)
CLONER_NO_UNPACK = config('CLONER_NO_UNPACK', default=False, cast=bool)
CLONER_SHALLOW = config('CLONER_SHALLOW', default=False, cast=bool)
CLONER_NOFILTER = config('CLONER_NOFILTER', default=False, cast=bool)

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


# What happens on failure?
@app.task(time_limit=(60 * 60),
          soft_time_limit=(55 * 60))
def process_snapshot(snapshot_pk, selection_pk=None):
    celery_logger.info(f"Running for {snapshot_pk}")
    snapshot = ProjectSnapshot.objects.get(pk=snapshot_pk)

    celery_logger.info('processing ProjectSnapshot: ' + snapshot.project.url)
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

    celery_logger.debug('    -> root:    ' + repo_root)
    celery_logger.debug('    -> name:    ' + project_name)
    celery_logger.debug('    -> base:    ' + project_base)
    celery_logger.debug('    -> path:    ' + path)
    celery_logger.debug('    -> tmp:     ' + tmp)
    celery_logger.debug('    -> src_dir: ' + src_dir)
    celery_logger.debug('    -> git_dir: ' + git_dir)

    if os.path.exists(path):
        celery_logger.info('    -> SKIPPING: already exists: ' + project_name)
        if selection_pk is not None:
            backend.tasks.filter_selection.delay(selection_pk)
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
        if selection_pk is not None:
            backend.tasks.filter_selection.delay(selection_pk)

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
                    celery_logger.info('root does not exist, creating: ' + repo_root)
                    os.makedirs(repo_root, 0o755, True)

                update_metrics(snapshot, src_dir)

                # move repo to final location
                parent_dir = os.path.join(repo_root, project_parent)
                celery_logger.info('moving ' + src_dir + ' -> ' + parent_dir)
                if not os.path.exists(parent_dir):
                    os.makedirs(parent_dir, 0o755, True)
                    shutil.move(src_dir, parent_dir)

                snapshot.path = os.path.join(host, project_name)
            else:
                celery_logger.info('failed to clone ' + project_name)

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
    celery_logger.info(path + ', commits =' + str(project.commits))

    # update number of committers
    p = subprocess.Popen(['git', 'shortlog', '-sne'], cwd=path, stdout=subprocess.PIPE, stderr=None)
    project.committers = p.stdout.read().decode().count('\n')
    celery_logger.info(path + ', committers =' + str(project.committers))

    # update src file counts
    # similar to: find . -name '*.java' | wc -l
    p = subprocess.Popen(['find', '.', '-name', '*.java'], cwd=path, stdout=subprocess.PIPE, stderr=None)
    project.src_files = p.stdout.read().decode().count('\n')
    celery_logger.info(path + ', src_files =' + str(project.src_files))
