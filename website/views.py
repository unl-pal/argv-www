import json

import os
import zipfile
from decouple import config

from django.http import HttpResponse, JsonResponse, Http404
from django.urls import reverse_lazy
from django.shortcuts import render, redirect, get_object_or_404
from django.views.generic import ListView, DetailView, View
from django.views.generic.edit import UpdateView, CreateView, DeleteView
from django.contrib import messages
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User
from django.contrib.auth.decorators import login_required
from django.db import transaction
from django.conf import settings
from django.core.mail import EmailMultiAlternatives
from django.template.loader import get_template
from django.contrib.sites.shortcuts import get_current_site
from django.utils.encoding import force_bytes, force_text
from django.utils.http import urlsafe_base64_encode, urlsafe_base64_decode
from django.template.loader import render_to_string
from django.core.mail import EmailMessage
from .mixins import EmailRequiredMixin
from .models import Paper, Profile, FilterDetail, Filter, ProjectSelector, ProjectTransformer, TransformedProject
from .forms import UserForm, UserPasswordForm, UserFormLogin, UserFormRegister, BioProfileForm, ProfileForm, ProjectSelectionForm, FilterDetailForm, FilterFormSet, EmailForm
from PIL import Image
from .tokens import email_verify_token
from .validators import validate_gh_token
from .decorators import email_required, email_verify_warning
from .choices import *

class PapersView(ListView):
    template_name = 'website/papers.html'
    context_object_name = 'allPapers'
    paginate_by = 25
    queryset = Paper.objects.all()
    ordering = ['-date']

class PeopleView(ListView):
    template_name = 'website/people.html'
    context_object_name = 'allPeople'
    paginate_by = 10
    queryset = User.objects.exclude(profile__staffStatus=USER)
    ordering = ['profile__staffStatus', 'last_name']

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
            send_email_verify(request, user, 'Verify your email with PAClab')
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
                        return email_verify_warning(request)
                    return redirect('website:index')
        return render(request, self.template_name, { 'form' : form })

class ProjectListView(EmailRequiredMixin, ListView):
    template_name = 'website/projects.html'
    context_object_name = 'projects'
    paginate_by = 20

    def get_queryset(self):
        objects = ProjectSelector.objects.all().filter(user=self.request.user)
        if not self.request.user.has_perm('website.view_disabled'):
            objects = objects.filter(enabled=True)
        return objects.order_by('-created')

def project_detail(request, slug):
    try:
        model = ProjectSelector.objects.get(slug=slug)
    except:
        raise Http404
    if model.enabled == False and request.user.has_perm('website.view_disabled') == False:
        raise Http404
    if request.method == 'POST':
        form = EmailForm(request.POST)
        if form.is_valid() and request.user == model.user or request.user.has_perm('website.view_projectselector'):
            send_list = form.cleaned_data['email'].split(',')
            to = []
            for user in send_list:
                name, username = user.split('(')
                username = username.rstrip(')')
                email = str(User.objects.get(username=username).email)
                to.append(email)
            user = str(request.user.username)
            url = request.build_absolute_uri('/project/detail/' + slug)
            variables = { 'user' : user, 'url' : url }
            msg_html = get_template('website/project_selection_email.html')
            text_content = 'A project has been shared with you!'
            html_content = msg_html.render(variables)
            msg = EmailMultiAlternatives('PAClab Project Selection', text_content, request.user.email, to)
            msg.attach_alternative(html_content, "text/html")
            msg.send()
            messages.success(request, 'Email invitation(s) sent')
        else:
            messages.warning(request, 'Invalid form entry')
    else:
        form = EmailForm()
        values = FilterDetail.objects.all().filter(project_selector=model)
    return render(request, 'website/project_detail.html', { 'project' : model, 'form' : form, 'values' : values, 'is_done' : model.isDone(), 'cloned' : len(model.project.exclude(host__isnull=True).exclude(path__isnull=True)) })

@email_required
def project_delete(request, slug):
    try:
        model = ProjectSelector.objects.get(slug=slug)
    except:
        raise Http404
    if request.method == 'POST':
        if request.user == model.user or request.user.has_perm('website.delete_projectselector'):
            model.enabled = False
            model.save()
            messages.info(request, 'You have deleted this project selection')
            return redirect('website:project_list')
        else:
            messages.warning(request, 'You are not the owner of this selection and cannot delete it')
    return render(request, 'website/delete.html')

def api_usernames(request):
    q = request.GET.get('term', '')
    results = []
    if len(q) > 2:
        for r in User.objects.filter(username__icontains=q).filter(is_active=True).filter(profile__active_email=True)[:10]:
            results.append(r.first_name + ' ' + r.last_name + ' (' + r.username + ')')
    return JsonResponse(results, safe=False)

def logoutView(request):
    logout(request)
    messages.success(request, 'You have successfully logged out.')
    return redirect('website:index')

@login_required
def profile(request):
    if request.method == 'POST':
        userForm = UserForm(request.POST, instance=request.user)
        if request.user.profile.hasBio():
            profileForm = BioProfileForm(request.POST, request.FILES, instance=request.user.profile)
        else:
            profileForm = ProfileForm(request.POST, request.FILES, instance=request.user.profile)
        if userForm.is_valid() and profileForm.is_valid():
            userForm.save()
            profileForm.save()
            if 'email' in userForm.changed_data:
                request.user.profile.active_email = False
                request.user.profile.save()
                send_email_verify(request, request.user, 'Verify your email with PAClab')

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
        if request.user.profile.hasBio():
            profileForm = BioProfileForm(instance=request.user.profile)
        else:
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
            messages.success(request, 'Project selection created successfully.')
            return redirect(reverse_lazy('website:project_detail', args=(selector.slug,)))
        messages.error(request, 'Invalid form entry')
    return render(request, template_name, {
        'p_form' : p_form,
        'formset': formset,
    })

def api_filter_default(request):
    val = int(request.GET.get('id', 0))
    try:
        pfilter = Filter.objects.get(pk=val)
    except:
        raise Http404
    return JsonResponse({ 'id' : val, 'default' : pfilter.default_val })

def verify_email_link(request):
    return send_email_verify(request, request.user, 'Reconfirm email address')

def send_email_verify(request, user, title):
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
    messages.info(request, "If an account exists with the email you entered, we've emailed you a link for verifying the email address. You should receive the email shortly. If you don't receive an email, check your spam/junk folder and please make sure your email address is entered correctly in your profile.")
    return redirect('website:index')

def download(request, slug):
    paths = []

    try:
        for project in ProjectSelector.objects.get(slug=slug).project.exclude(path__isnull=True):
            transformed_project = project.transformedproject_set.exclude(path__isnull=True).first()
            if transformed_project:
                paths.append((transformed_project.host, transformed_project.path))
    except:
        raise Http404

    if not paths:
        raise Http404

    download_path = os.path.join(settings.MEDIA_ROOT, 'downloads')
    download_filename = slug + '.zip'
    zipfile_path = os.path.join(download_path, download_filename)

    if not os.path.exists(zipfile_path):
        if not os.path.exists(download_path):
            os.mkdir(download_path, 0o755)

        transformed_path = config('TRANSFORMED_PATH')

        archive = zipfile.ZipFile(zipfile_path, 'a')
        for path in paths:
            for dirname, subdirs, files in os.walk(os.path.join(transformed_path, os.path.join(path[0], path[1]))):
                for filename in files:
                    full_path = os.path.join(dirname, filename)
                    first, arcname = full_path.split(os.path.join(transformed_path, path[0]))
                    archive.write(full_path, arcname)
        archive.close()

    return redirect(settings.MEDIA_URL + '/downloads/' + download_filename)
