from django.contrib.auth.mixins import LoginRequiredMixin
from django.shortcuts import redirect
from django.contrib import messages
from .models import Profile

class EmailRequiredMixin(LoginRequiredMixin):
    """Verify that the current user has an active email."""
    def dispatch(self, request, *args, **kwargs):
        if request.user.is_authenticated:
            if not request.user.profile.active_email:
                messages.warning(request, ('Your email account has not been activated.'))
                # Change to redirect to verification email page
                return self.handle_no_permission()
        else:
            return self.handle_no_permission()
        return super().dispatch(request, *args, **kwargs)
