from django.conf import settings

from .middleware import SiteMaintenanceMiddleware


def adminConstant(self):
    return { 'ADMIN' : 'Admin' }

def moderatorConstant(self):
    return { 'MODERATOR' : 'Moderator' }

def retiredConstant(self):
    return { 'RETIRED' : 'Retired' }

def useHijack(self):
    return { 'USE_HIJACK' : settings.USE_HIJACK }

def in_maintenance(self):
    return { 'IS_MAINT_MODE' : SiteMaintenanceMiddleware.in_maintenance_mode }
