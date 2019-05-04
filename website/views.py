from django.http import HttpResponse, JsonResponse
from django.shortcuts import render, redirect, get_object_or_404
from django.views.generic import ListView, DetailView, View
from django.views.generic.edit import UpdateView, CreateView, DeleteView
from django.contrib import messages
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User
from django.contrib.auth.decorators import login_required
from django.db import transaction
from django.conf import settings
from django.contrib.sites.shortcuts import get_current_site
from django.utils.encoding import force_bytes, force_text
from django.utils.http import urlsafe_base64_encode, urlsafe_base64_decode
from django.template.loader import render_to_string
from django.core.mail import EmailMessage
from .models import Paper, Profile, FilterDetail, ProjectSelector, Filter
from .forms import UserForm, UserPasswordForm, UserFormLogin, UserFormRegister, ProfileForm, ProjectSelectionForm, FilterDetailForm, FilterFormSet
from PIL import Image
from .tokens import email_verify_token
from .validators import validate_gh_token

class PapersView(ListView):
    template_name='website/papers.html'
    context_object_name='allPapers'
    def get_queryset(self):
        return Paper.objects.all()

class PeopleView(ListView):
    template_name='website/people.html'
    context_object_name = 'allPeople'
    def get_queryset(self):
        return User.objects.all().order_by('last_name')

class RegisterView(View):
    form_class = UserFormRegister
    template_name = 'website/register.html'
    def get(self, request):
        form = self.form_class(None)
        return render(request, self.template_name, { 'form' : form })

    def post(self, request):
        form = self.form_class(request.POST)
        if form.is_valid():
            user = form.save()
            user.profile.privacy_agreement = form.cleaned_data['privacy_agreement']
            user.profile.terms_agreement = form.cleaned_data['terms_agreement']
            user.profile.age_confirmation = form.cleaned_data['age_confirmation']
            user.profile.token = form.cleaned_data['token']
            user.profile.save()
            login(request, user)
            verify_email_link(request, user, 'Verify your email with PAClab')
            messages.info(request, 'Please check and confirm your email to complete registration.')
            return redirect('website:index')
        return render(request, self.template_name, { 'form' : form })

def verify_email(request, uidb64, token):
    try:
        uid = urlsafe_base64_decode(uidb64)
        user = User.objects.get(pk=uid)
    except(TypeError, ValueError, OverflowError, User.DoesNotExist):
        user = None
    if user is not None and email_verify_token.check_token(user, token):
        user.profile.active_email = True
        user.profile.save()
        login(request, user)
    messages.info(request, 'If you followed a valid email verification link, your email is now verified.')
    return redirect('website:index')

class LoginView(View):
    form_class = UserFormLogin
    template_name = 'website/login.html'

    def get(self, request):
        form = self.form_class(None)
        return render(request, self.template_name, { 'form' : form })

    def post(self, request):
        form = self.form_class(request.POST)
        if form.is_valid():
            username = form.cleaned_data['username']
            password = form.cleaned_data['password']
            user = authenticate(request, username=username, password=password)
            if user is not None:
                if user.is_active:
                    login(request, user)
                    if not user.profile.active_email:
                        messages.warning(request, ('Your email address is not yet verified!  Please verify email from your profile page.'))
                    return redirect('website:index')
        return render(request, self.template_name, { 'form' : form })

def logoutView(request):
    logout(request)
    messages.success(request, 'You have successfully logged out.')
    return redirect('website:index')

@login_required
def profile(request):
    if request.method == 'POST':
        userForm = UserForm(request.POST, instance=request.user)
        profileForm = ProfileForm(request.POST, request.FILES, instance=request.user.profile)
        if userForm.is_valid() and profileForm.is_valid():
            userForm.save()
            profileForm.save()
            if 'email' in userForm.changed_data:
                request.user.profile.active_email = False
                request.user.profile.save()
                verify_email_link(request, request.user, 'Verify your email with PAClab')

            if profileForm.cleaned_data['photo']:
                image = Image.open(request.user.profile.photo)

                try:
                    x = float(request.POST.get('crop_x', 0))
                    y = float(request.POST.get('crop_y', 0))
                    w = float(request.POST.get('crop_w', 0))
                    h = float(request.POST.get('crop_h', 0))
                    if x and y and w and h:
                        image = image.crop((x, y, w + x, h + y))
                except:
                    pass

                image = image.resize(settings.THUMBNAIL_SIZE, Image.LANCZOS)
                image.save(request.user.profile.photo.path)

            messages.success(request, 'Profile successfully updated')
            return redirect('website:edit_profile')
        else:
            messages.error(request, 'Invalid form entry')
    else:    
        userForm = UserForm(instance=request.user)
        profileForm = ProfileForm(instance=request.user.profile)
    return render(request, 'website/editprofile.html', { 'userForm' : userForm, 'profileForm' : profileForm, 'min_width' : settings.THUMBNAIL_SIZE, 'min_height' : settings.THUMBNAIL_SIZE })

@login_required
def password_change(request):
    if request.method == 'POST':
        form = UserPasswordForm(request.user, request.POST)
        if form.is_valid():
            form.save()
            messages.success(request, 'Password updated!')
            return redirect('website:index')
        messages.error(request, 'Invalid form entry')
    else:
        form = UserPasswordForm(request.user)
    return render(request, 'website/password_change.html', { 'form' : form })

def project_selection(request):
    try:
        validate_gh_token(request.user.profile.token)
    except:
        request.user.profile.token = ''
        request.user.profile.save()
        messages.error(request, 'Your GitHub token is no longer valid. You must fix it.')
        return redirect('website:edit_profile')

    template_name = 'website/create_normal.html'
    heading_message = 'Project Selection'
    if request.method == 'GET':
        p_form = ProjectSelectionForm(request.GET or None)
        formset = FilterFormSet(request.GET or None)
    elif request.method == 'POST':
        p_form = ProjectSelectionForm(request.POST)
        formset = FilterFormSet(request.POST)
        if p_form.is_valid() and formset.is_valid():
            selector = ProjectSelector()
            selector.user = request.user
            selector.input_dataset = p_form.cleaned_data['input_dataset']
            selector.save()
            for form in formset:
                pfilter = form.cleaned_data.get('pfilter')
                value = form.cleaned_data.get('value')
                if value and pfilter:
                    try:
                        connection = FilterDetail()
                        connection.project_selector = ProjectSelector.objects.all().last()
                        pk = form.cleaned_data.get('pfilter').id
                        connection.pfilter = Filter.objects.get(pk=pk)
                        connection.value = form.cleaned_data['value']
                        connection.save()
                    except:
                        pass
            messages.success(request, ('Form saved'))
            return redirect('website:project_selection')
        messages.error(request, ('Invalid form entry'))
    return render(request, template_name, {
        'p_form' : p_form,
        'formset': formset,
        'heading': heading_message,
    })

def data_default(request):
    text = request.GET.get('text', None)
    pfilter = Filter.objects.get(name=text)
    data = pfilter.val_type
    default = pfilter.default_val
    return JsonResponse({ 'data' : data, 'default' : default })

def verify_email_link(request):
    verify_email_link(request, request.user, 'Reconfirm email address')
    return redirect('website:verify_email_confirm')

def verify_email_link(request, user, title):
    current_site = get_current_site(request)
    message = render_to_string('website/email_verification_email.html', {
        'user' : user,
        'domain' : current_site.domain,
        'uid' : urlsafe_base64_encode(force_bytes(user.pk)),
        'token' : email_verify_token.make_token(user),
    })
    to_email = user.email
    email = EmailMessage(title, message, to=[to_email])
    email.send()
    return redirect('website:verify_email_confirm')

def verify_email_confirm(request):
    return render(request, 'website/verify_email_confirm.html')
