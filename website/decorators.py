from django.contrib import messages
from django.shortcuts import redirect
from django.urls import reverse_lazy

def email_required(function):
    def wrap(request, *args, **kwargs):
        if request.user.profile.active_email:
            return function(request, *args, **kwargs)
        else:
            messages.warning(request, ('You have not activated your email account yet, please do so from the profile page.'))
            return redirect(reverse_lazy('website:edit_profile'))
    wrap.__doc__ = function.__doc__
    wrap.__name__ = function.__name__
    return wrap
