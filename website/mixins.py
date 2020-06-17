from django.contrib.auth.mixins import LoginRequiredMixin

from .decorators import email_verify_warning

class EmailRequiredMixin(LoginRequiredMixin):
    """Verify that the current user has a verified email."""

    permission_denied_message = ''

    def dispatch(self, request, *args, **kwargs):
        if not request.user.is_authenticated:
            return self.handle_no_permission()
        if not request.user.profile.active_email:
            self.permission_denied_message = 'You must have a verified email address to view this page.'
            return email_verify_warning(request)
        return super().dispatch(request, *args, **kwargs)

    def get_permission_denied_message(self):
        return self.permission_denied_message

class DeletableReadOnlyAdminMixin(object):
    """Makes a ModelAdmin read only and disables adds/edits but allows for deletes."""

    def has_add_permission(self, request):
        return False

    def has_delete_permission(self, request, obj=None):
        return True

    def has_change_permission(self, request, obj=None):
        return False

    def save_model(self, request, obj, form, change):
        pass

    def save_related(self, request, form, formsets, change):
        pass

class ReadOnlyAdminMixin(DeletableReadOnlyAdminMixin):
    """Makes a ModelAdmin read only and disables adds/edits/deletes."""

    def has_delete_permission(self, request, obj=None):
        return False
    def delete_model(self, request, obj):
        pass
