from django.contrib import admin
from .models import Backend, SupportedFilter

admin.site.register(Backend)
admin.site.register(SupportedFilter)
