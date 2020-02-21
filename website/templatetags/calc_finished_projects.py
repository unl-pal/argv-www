from django import template

from website.models import TransformedProject

register = template.Library()

@register.simple_tag(name='calc_finished_projects')
def calc_finished_projects(selector):
    qs1 = selector.project.filter(path__isnull=False).values_list('id')
    qs2 = TransformedProject.objects.filter(path__isnull=False).values_list('project')
    return qs1.intersection(qs2).count()

@register.simple_tag(name='calc_finished_transformedprojects')
def calc_finished_transformedprojects(transformer):
    return 0
    # qs1 = transformer.project_selector.project.filter(path__isnull=False).values_list('id')
    # qs2 = transformer.transformed_projects.filter(path__isnull=False).values_list('project')
    # return qs1.intersection(qs2).count()

@register.simple_tag(name='calc_remaining_projects')
def calc_remaining_projects(selector):
    qs1 = selector.project.filter(path__isnull=False).values_list('id')
    qs2 = TransformedProject.objects.filter(host__isnull=False).values_list('project')
    return qs1.difference(qs2).count()

@register.simple_tag(name='calc_remaining_transformedprojects')
def calc_remaining_transformedprojects(transformer):
    return 0
    # qs1 = transformer.project_selector.project.filter(path__isnull=True).values_list('id')
    # qs2 = transformer.transformed_projects.filter(host__isnull=True).values_list('project')
    # return qs1.difference(qs2).count()

@register.simple_tag(name='calc_finished_percent')
def calc_finished_percent(done, remain):
    if done + remain == 0:
        return 100
    return round(100.0 * done / (done + remain), 2)
