from django import template

register = template.Library()

@register.filter(name='calc_finished_projects')
def calc_finished_projects(selector):
    count = 0
    for project in selector.project.all():
        if project.transformedproject_set.first().path:
            count += 1
    return count
