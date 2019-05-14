from django.contrib import admin
from .models import Backend

class BackendAdmin(admin.ModelAdmin):
    model = Backend
    readonly_fields = ['name','enabled']

    def has_add_permission(self, request, obj=None):
        return False

    def has_delete_permission(self, request, obj=None):
        return False

admin.site.register(Backend, BackendAdmin)
