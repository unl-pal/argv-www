from django.conf import settings
from django.contrib import messages
from django.http import HttpResponse
from django.template import loader
from django.utils.deprecation import MiddlewareMixin

from website import urls


class SiteMaintenanceMiddleware(MiddlewareMixin):
    in_maintenance_mode = getattr(settings, 'MAINTENANCE_MODE')

    def page_whitelisted(self, url):
        url = url[1:] # strip off preceding '/'

        # all white-listed pages are at the start of the urlpatterns
        for p in urls.urlpatterns[0:6]:
            if p.resolve(url):
                return True

        return False

    def process_view(self, request, view_func, view_args, view_kwargs):
        if SiteMaintenanceMiddleware.in_maintenance_mode:
            if not self.page_whitelisted(request.path):
                if not request.user.is_staff:
                    return HttpResponse(loader.render_to_string('website/maintenance.html'), status=503)
        return None
