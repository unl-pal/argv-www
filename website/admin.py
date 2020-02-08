from django.contrib import admin
from django.utils.html import mark_safe, format_html
from django.urls import reverse_lazy
from .models import TransformedProject, Paper, Profile, Dataset, ProjectSelector, Project, Filter, ProjectTransformer, Selection, Transform, Analysis, FilterDetail, UserAuthAuditEntry

class ProfileAdmin(admin.ModelAdmin):
    model = Profile
    list_display = ['get_username', 'get_email', 'get_firstname', 'get_lastname', ]
    search_fields = ['user__first_name', 'user__last_name', 'user__email', ]

    def get_username(self, obj):
        return obj.user.username
    get_username.admin_order_field  = 'user__username'
    get_username.short_description = 'Username'

    def get_firstname(self, obj):
        return obj.user.first_name
    get_firstname.admin_order_field  = 'user__first_name'
    get_firstname.short_description = 'First Name'

    def get_lastname(self, obj):
        return obj.user.last_name
    get_lastname.admin_order_field  = 'user__last_name'
    get_lastname.short_description = 'Last Name'

    def get_email(self, obj):
        return obj.user.email
    get_email.admin_order_field  = 'user__email'
    get_email.short_description = 'Email Address'

class FilterDetailSelectionInline(admin.TabularInline):
    model = FilterDetail
    extra = 1

class SelectionAdmin(admin.ModelAdmin):
    list_display = ['enabled', 'slug', 'user', 'display_url', ]
    list_display_links = ['slug', ]
    list_filter = ['enabled', ('user', admin.RelatedOnlyFieldListFilter), ]
    inlines = (FilterDetailSelectionInline,)
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
    disable.allowed_permissions = ('change',)

    def enable(self, request, queryset):
        rows_updated = queryset.update(enabled=True)
        if rows_updated == 1:
            message_bit = "1 project selector was"
        else:
            message_bit = "%s project selectors were" % rows_updated
        self.message_user(request, "%s successfully enabled." % message_bit)
    enable.short_description = "Enable selected project selectors"
    disable.allowed_permissions = ('change',)

    def has_add_permission(self, request, obj=None):
        return False

    def display_url(self, obj):
        return format_html('<a href="{0}">details page</a>', reverse_lazy('website:project_detail', args=(obj.slug,)))
    display_url.short_description = 'Details Page'

class UserAuthAuditEntryAdmin(admin.ModelAdmin):
    list_display = ['action', 'datetime', 'user', 'attempted', 'hijacker', 'hijacked', 'ip', ]
    list_filter = ['action', 'hijacker', 'hijacked', ]

    def has_add_permission(self, request, obj=None):
        return False

    def has_change_permission(self, request, obj=None):
        return False

    def has_delete_permission(self, request, obj=None):
        return False

class FilterAdmin(admin.ModelAdmin):
    list_display = ['name', 'val_type', 'default_val', 'enabled', 'associated_backend', ]
    list_filter = ['enabled', 'associated_backend', ]

    def has_add_permission(self, request, obj=None):
        return False

    def has_change_permission(self, request, obj=None):
        return False
    
    def has_delete_permission(self, request, obj=None):
        return False

admin.site.register(Paper)
admin.site.register(Profile, ProfileAdmin)
admin.site.register(Dataset)
admin.site.register(ProjectSelector, SelectionAdmin)
admin.site.register(Project)
admin.site.register(Filter, FilterAdmin)
admin.site.register(ProjectTransformer)
admin.site.register(Selection)
admin.site.register(Transform)
admin.site.register(Analysis)
admin.site.register(TransformedProject)
admin.site.register(UserAuthAuditEntry, UserAuthAuditEntryAdmin)
