from django import template

register = template.Library()

@register.filter(name='project_done')
def project_done(project):
    return project.isDone()
