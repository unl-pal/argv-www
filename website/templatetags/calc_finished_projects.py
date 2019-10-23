from django import template
from website.models import TransformedProject

register = template.Library()

@register.filter(name='calc_finished_projects')
def calc_finished_projects(selector):
    # Wip
    return selector
