from django.contrib import messages
from django.contrib.auth.decorators import login_required
from django.shortcuts import redirect
from django.urls import reverse
from django.utils.safestring import mark_safe

def email_verify_warning(request):
    messages.warning(request, mark_safe('Your email address has not been verified. You can <a href="' + reverse('website:verify_email_link', current_app='website') + '">re-send the email verification</a> or <a href="' + reverse('website:edit_profile', current_app='website') + '">edit your profile</a> and change your email address.'))
    return redirect('website:index')

def email_required(function):
    @login_required
    def wrap(request, *args, **kwargs):
        if request.user.profile.active_email:
            return function(request, *args, **kwargs)
        return email_verify_warning(request)
    wrap.__doc__ = function.__doc__
    wrap.__name__ = function.__name__
    return wrap
