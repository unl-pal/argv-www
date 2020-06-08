from django import template

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
    return ProjectSnapshot.objects.filter(selection__project_selector=selector).filter(selection__retained=True).count()

#
# transforms
#
@register.simple_tag(name='calc_finished_transformedprojects')
def calc_finished_transformedprojects(transformer):
    return 0
    # qs1 = transformer.src_selector.project.filter(path__isnull=False).values_list('id')
    # qs2 = transformer.transformed_projects.filter(path__isnull=False).values_list('project')
    # return qs1.intersection(qs2).count()

@register.simple_tag(name='calc_remaining_transformedprojects')
def calc_remaining_transformedprojects(transformer):
    return 0
    # qs1 = transformer.src_selector.project.filter(path__isnull=True).values_list('id')
    # qs2 = transformer.transformed_projects.filter(host__isnull=True).values_list('project')
    # return qs1.difference(qs2).count()

@register.simple_tag(name='calc_finished_percent')
def calc_finished_percent(done, remain):
    if done + remain == 0:
        return 100
    return round(100.0 * done / (done + remain), 2)
