from django.contrib import admin
from .models import Backend
from website.mixins import ReadOnlyAdminMixin

class BackendAdmin(ReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['enabled', 'name', ]
    list_filter = ['enabled', ]

admin.site.register(Backend, BackendAdmin)
