from django.contrib import admin
from .models import Backend
from website.mixins import ReadOnlyAdminMixin

@admin.register(Backend)
class BackendAdmin(ReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['enabled', 'name', ]
    list_filter = ['enabled', ]
