from django import template

from website.choices import MANUAL_DATASET
from website.models import ProjectSnapshot, TransformedProject

register = template.Library()

#
# project selections
#
@register.simple_tag(name='calc_finished_projects')
def calc_finished_projects(selector):
    return ProjectSnapshot.objects.filter(selection__project_selector=selector).filter(selection__retained__isnull=False).count()

@register.simple_tag(name='calc_remaining_projects')
def calc_remaining_projects(selector):
    return ProjectSnapshot.objects.filter(selection__project_selector=selector).filter(selection__retained__isnull=True).count()

@register.simple_tag(name='calc_retained_projects')
def calc_retained_projects(selector):
    return selector.project_count()

@register.simple_tag(name='calc_finished_percent')
def calc_finished_percent(done, remain):
    if done + remain == 0:
        return 100
    return round(100.0 * done / (done + remain), 2)

@register.simple_tag(name='can_duplicate')
def can_duplicate(selector):
    return selector.input_dataset.name != MANUAL_DATASET
