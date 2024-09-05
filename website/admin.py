from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from django.contrib.auth.models import User
from django.forms import BaseInlineFormSet
from django.urls import reverse_lazy
from django.utils.html import format_html

from website.models import (BackendFilter, TransformParameter,
                            TransformParameterValue)

from .mixins import DeletableReadOnlyAdminMixin, ReadOnlyAdminMixin
from .models import (Dataset, Filter, FilterDetail, Paper, Profile, Project,
                     ProjectSelector, ProjectSnapshot, ProjectTransformer,
                     Selection, Transform, TransformedProject, TransformOption,
                     TransformSelection, UserAuthAuditEntry)

class ProfileAdmin(admin.StackedInline):
    model = Profile
    can_delete = False

class UserProfileAdmin(UserAdmin):
    inlines = [ProfileAdmin, ]

admin.site.unregister(User)
admin.site.register(User, UserProfileAdmin)

class FilterDetailSelectionInline(ReadOnlyAdminMixin,admin.TabularInline):
    model = FilterDetail
    extra = 0

@admin.register(ProjectSelector)
class SelectionAdmin(admin.ModelAdmin):
    list_display = ['enabled', 'slug', 'user', 'display_url', ]
    list_display_links = ['slug', ]
    list_filter = ['enabled', ('user', admin.RelatedOnlyFieldListFilter), ]
    inlines = [FilterDetailSelectionInline, ]
    readonly_fields = ['parent', 'download_count', 'last_download', ]
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
    enable.allowed_permissions = ('change', )

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
    readonly_fields = ['parent', 'src_selector', 'src_transformer', 'transform', 'download_count', 'last_download', ]
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
    enable.allowed_permissions = ('change', )

    def has_add_permission(self, request, obj=None):
        return False

    def display_url(self, obj):
        return format_html('<a href="{0}">details page</a>', reverse_lazy('website:transform_detail', args=(obj.slug,)))
    display_url.short_description = 'Details Page'

@admin.register(UserAuthAuditEntry)
class UserAuthAuditEntryAdmin(ReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['action', 'datetime', 'user', 'attempted', 'hijacker', 'hijacked', 'ip', ]
    list_filter = ['action', 'hijacker', 'hijacked', ]

class SnapshotsInline(admin.TabularInline):
    class LimitSnapshotsFormset(BaseInlineFormSet):
        def __init__(self, *args, **kwargs):
            super().__init__(*args, **kwargs)
            _kwargs = { self.fk.name: kwargs['instance'] }
            self.queryset = kwargs['queryset'].filter(**_kwargs).order_by('-datetime_processed')[:50]

    verbose_name_plural = "Last 50 Snapshots"
    model = ProjectSnapshot
    extra = 0
    formset = LimitSnapshotsFormset

@admin.register(Project)
class ProjectAdmin(DeletableReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['url', 'dataset', ]
    search_fields = ['url', ]
    list_filter = ['dataset', ]
    inlines = [SnapshotsInline, ]

@admin.register(ProjectSnapshot)
class ProjectSnapshotAdmin(admin.ModelAdmin):
    list_display = ['project', 'host', 'path', 'datetime_processed', 'commits', 'committers', 'src_files', ]
    list_filter = ['host', ]
    search_fields = ['project__url', ]
    readonly_fields = ['project', ]
    fields = ['project', 'host', 'path', 'datetime_processed', 'commits', 'committers', 'src_files', ]

    def has_add_permission(self, request):
        return False

@admin.register(Selection)
class SelectionAdmin(DeletableReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['project_selector', 'snapshot', 'retained', ]
    list_filter = ['retained', ]

@admin.register(Filter)
class FilterAdmin(ReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['name', 'val_type', 'default_val', 'enabled', ]
    list_filter = ['enabled', ]

@admin.register(BackendFilter)
class BackendFilterAdmin(ReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['backend', 'flter', 'enabled', ]
    list_filter = ['enabled', 'backend', ]

@admin.register(Transform)
class TransformAdmin(ReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['name', 'enabled', 'associated_backend', ]
    list_filter = ['enabled', 'associated_backend', ]

@admin.register(TransformedProject)
class TransformedProjectAdmin(admin.ModelAdmin):
    list_display = ['path', 'host', 'src_url', 'transform', ]
    list_filter = ['host', 'transform', ]
    readonly_fields = ['transform', 'src_url', ]
    search_fields = ['path', 'host', ]
    fields = ['src_url', 'transform', 'host', 'path', 'datetime_processed', ]

    def has_add_permission(self, request):
        return False

    def has_delete_permission(self, request, obj=None):
        return False

    def delete_model(self, request, obj):
        pass

    def src_url(self, obj):
        if obj.src_project:
            return format_html('Project: <a href="{0}">{1}</a>', reverse_lazy('admin:website_project_change', args=(obj.src_project.pk,)), obj.src_project)
        return format_html('Transform: <a href="{0}">{1}</a>', reverse_lazy('admin:website_transform_change', args=(obj.src_trasnform.pk,)), obj.src_trasnform)
    src_url.short_description = 'Source'

@admin.register(TransformSelection)
class TransformSelectionAdmin(ReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['transformer', 'transformed_project', ]
    search_fields = ['transformer', ]

@admin.register(TransformOption)
class TransformOptionAdmin(DeletableReadOnlyAdminMixin,admin.ModelAdmin):
    list_display = ['transform', ]
    list_filter = ['transform', ]

admin.site.register(Paper)
admin.site.register(Dataset)
admin.site.register(TransformParameter)
admin.site.register(TransformParameterValue)

admin.site.site_header = 'PAClab Admin'
admin.site.site_title = 'PAClab Admin'
admin.site.index_title = ''
