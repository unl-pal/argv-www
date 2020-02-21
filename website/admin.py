from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from django.contrib.auth.models import User
from django.urls import reverse_lazy
from django.utils.html import format_html

from paclab.settings import USE_HIJACK

from .mixins import ReadOnlyAdminMixin
from .models import (
    Dataset, Filter, FilterDetail, Paper, Profile, Project, ProjectSelector,
    ProjectTransformer, Selection, Transform, TransformOption,
    TransformSelection, TransformedProject, UserAuthAuditEntry)
if USE_HIJACK:
    from hijack_admin.admin import HijackUserAdmin

class ProfileAdmin(admin.StackedInline):
    model = Profile
    can_delete = False

if USE_HIJACK:
    class UserProfileAdmin(HijackUserAdmin):
        inlines = [ProfileAdmin, ]
else:
    class UserProfileAdmin(UserAdmin):
        inlines = [ProfileAdmin, ]

admin.site.unregister(User)
admin.site.register(User, UserProfileAdmin)

class FilterDetailSelectionInline(admin.TabularInline):
    model = FilterDetail
    extra = 1

@admin.register(ProjectSelector)
class SelectionAdmin(admin.ModelAdmin):
    list_display = ['enabled', 'slug', 'user', 'display_url', ]
    list_display_links = ['slug', ]
    list_filter = ['enabled', ('user', admin.RelatedOnlyFieldListFilter), ]
    inlines = [FilterDetailSelectionInline, ]
    search_fields = ['slug', ]
    actions = ['disable', 'enable', ]

    def disable(self, request, queryset):
        rows_updated = queryset.update(enabled=False)
        if rows_updated == 1:
            message_bit = "1 project selector was"
        else:
            message_bit = "%s project selectors were" % rows_updated
        self.message_user(request, "%s successfully disabled." % message_bit)
    disable.short_description = "Disable selected project selectors"
    disable.allowed_permissions = ('change', )

    def enable(self, request, queryset):
        rows_updated = queryset.update(enabled=True)
        if rows_updated == 1:
            message_bit = "1 project selector was"
        else:
            message_bit = "%s project selectors were" % rows_updated
        self.message_user(request, "%s successfully enabled." % message_bit)
    enable.short_description = "Enable selected project selectors"
    disable.allowed_permissions = ('change', )

    def has_add_permission(self, request, obj=None):
        return False

    def display_url(self, obj):
        return format_html('<a href="{0}">details page</a>', reverse_lazy('website:selection_detail', args=(obj.slug,)))
    display_url.short_description = 'Details Page'

@admin.register(ProjectTransformer)
class TransformerAdmin(admin.ModelAdmin):
    list_display = ['enabled', 'slug', 'user', 'display_url', ]
    list_display_links = ['slug', ]
    list_filter = ['enabled', ('user', admin.RelatedOnlyFieldListFilter), ]
    search_fields = ['slug', ]
    actions = ['disable', 'enable', ]

    def disable(self, request, queryset):
        rows_updated = queryset.update(enabled=False)
        if rows_updated == 1:
            message_bit = "1 project transformer was"
        else:
            message_bit = "%s project transformers were" % rows_updated
        self.message_user(request, "%s successfully disabled." % message_bit)
    disable.short_description = "Disable selected project transformers"
    disable.allowed_permissions = ('change', )

    def enable(self, request, queryset):
        rows_updated = queryset.update(enabled=True)
        if rows_updated == 1:
            message_bit = "1 project transformer was"
        else:
            message_bit = "%s project transformers were" % rows_updated
        self.message_user(request, "%s successfully enabled." % message_bit)
    enable.short_description = "Enable selected project transformers"
    disable.allowed_permissions = ('change', )

    def has_add_permission(self, request, obj=None):
        return False

    def display_url(self, obj):
        return format_html('<a href="{0}">details page</a>', reverse_lazy('website:transform_detail', args=(obj.slug,)))
    display_url.short_description = 'Details Page'

@admin.register(UserAuthAuditEntry)
class UserAuthAuditEntryAdmin(ReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['action', 'datetime', 'user', 'attempted', 'hijacker', 'hijacked', 'ip', ]
    list_filter = ['action', 'hijacker', 'hijacked', ]

@admin.register(Filter)
class FilterAdmin(ReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['name', 'val_type', 'default_val', 'enabled', 'associated_backend', ]
    list_filter = ['enabled', 'associated_backend', ]

@admin.register(Transform)
class TransformAdmin(ReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['name', 'enabled', 'associated_backend', ]
    list_filter = ['enabled', 'associated_backend', ]

admin.site.register(Paper)
admin.site.register(Dataset)
admin.site.register(Project)
admin.site.register(Selection)
admin.site.register(TransformOption)
admin.site.register(TransformSelection)
admin.site.register(TransformedProject)

admin.site.site_header = 'PAClab Admin'
admin.site.site_title = 'PAClab Admin'
admin.site.index_title = ''
