from django import template

register = template.Library()

@register.filter(name='calc_cloned_projects')
def calc_cloned_projects(selector):
    count = 0
    for project in selector.project.all():
        if project.host and project.datetime_processed != '' and project.path:
            count += 1
    return count

@register.simple_tag
def url_replace(request, field, value):
    query_string = request.GET.copy()
    query_string[field] = value

    return query_string.urlencode()

@register.filter(name='proper_paginate')
def proper_paginate(paginator, current_page, neighbors=5):
    width = 2 * neighbors
    if paginator.num_pages > width:
        start_index = max(1, current_page - neighbors)
        end_index = min(paginator.num_pages, current_page + neighbors)

        if end_index < start_index + width:
            end_index = start_index + width
        elif start_index > end_index - width:
            start_index = end_index - width

        if start_index < 1:
            end_index -= start_index
            start_index = 1
        elif end_index > paginator.num_pages:
            start_index -= (end_index - paginator.num_pages)
            end_index = paginator.num_pages

        page_list = [f for f in range(start_index, end_index + 1)]
        return page_list[:(width + 1)]
    return paginator.page_range
