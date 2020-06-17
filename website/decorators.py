from django.contrib import messages
from django.contrib.auth.decorators import login_required
from django.shortcuts import redirect
from django.urls import reverse
from django.utils.safestring import mark_safe

from website.validators import validate_gh_token

def email_verify_warning(request):
    messages.warning(request, mark_safe('Your email address has not been verified. You can <a href="' + reverse('website:verify_email_link', current_app='website') + '">re-send the email verification</a> or <a href="' + reverse('website:edit_profile', current_app='website') + '">edit your profile</a> and change your email address.'))
    return redirect('website:index')

def email_required(function):
    @login_required
    def wrap(request, *args, **kwargs):
        if not request.user.profile.active_email:
            return email_verify_warning(request)
        return function(request, *args, **kwargs)
    wrap.__doc__ = function.__doc__
    wrap.__name__ = function.__name__
    return wrap

def token_verify_warning(request):
    messages.error(request, mark_safe('You must provide a valid GitHub token to use the requested resource.'))
    return redirect('website:edit_profile')

def ghtoken_required(function):
    @login_required
    def wrap(request, *args, **kwargs):
        try:
            validate_gh_token(request.user.profile.token)
        except:
            request.user.profile.token = ''
            request.user.profile.save()
            return token_verify_warning(request)
        return function(request, *args, **kwargs)
    wrap.__doc__ = function.__doc__
    wrap.__name__ = function.__name__
    return wrap
