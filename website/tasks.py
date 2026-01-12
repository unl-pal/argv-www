#!/usr/bin/env python
# coding: utf-8

from celery import shared_task
from celery.utils.log import get_task_logger
logger = get_task_logger(__name__)

from django.core.mail import EmailMessage


@shared_task
def send_email(subject, message, **kwargs):
    email = EmailMessage(subject, message, **kwargs)
    email.send()
