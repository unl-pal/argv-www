from django.contrib.auth.mixins import LoginRequiredMixin
from django.shortcuts import redirect
from django.contrib import messages
from .models import Profile

class EmailRequiredMixin(LoginRequiredMixin):
    """Verify that the current user has an active email."""

    permission_denied_message = ''

    def dispatch(self, request, *args, **kwargs):
        if request.user.is_authenticated:
            if not request.user.profile.active_email:
                messages.warning(request, ('Your email account has not been activated.'))
                # Change to redirect to verification email page
                self.permission_denied_message = 'You do not have an active email account and cannot acess this page.'
                return self.handle_no_permission()
        else:
            self.permission_denied_message = 'Please login to access this page.'
            return redirect('website:login')
        return super().dispatch(request, *args, **kwargs)
    
    def get_permission_denied_message(self):
        return self.permission_denied_message
