from django.contrib.auth.mixins import LoginRequiredMixin
from django.urls import reverse
from django.utils.safestring import mark_safe
from django.shortcuts import redirect
from django.contrib import messages
from .models import Profile

class EmailRequiredMixin(LoginRequiredMixin):
    """Verify that the current user has a verified email."""

    permission_denied_message = ''

    def dispatch(self, request, *args, **kwargs):
        if request.user.is_authenticated:
            if not request.user.profile.active_email:
                messages.warning(request, mark_safe('Your email address has not been verified. You can <a href="' + reverse('website:activate_email', current_app='website') + '">re-send the email verification</a> or <a href="' + reverse('website:editProfile', current_app='website') + '">edit your profile</a> and change your email address.'))
                self.permission_denied_message = 'You must have a verified email address to view this page.'
                return redirect('website:index')
        else:
            self.permission_denied_message = 'Please login to access this page.'
            return redirect('website:login')
        return super().dispatch(request, *args, **kwargs)
 
    def get_permission_denied_message(self):
        return self.permission_denied_message
