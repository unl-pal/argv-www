from django import template

register = template.Library()

@register.filter(name='calc_finished_projects')
def calc_finished_projects(selector):
    count = 0
    for project in selector.project.filter(path__isnull=False):
        count += project.transformedproject_set.filter(path__isnull=False).count()
    return count
