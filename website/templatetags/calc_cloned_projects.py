from django import template

register = template.Library()

@register.filter(name='calc_cloned_projects')
def calc_cloned_projects(selector):
    count = 0
    for project in selector.project.all():
        if project.host and project.datetime_processed != '' and project.path:
            count += 1
    return count
