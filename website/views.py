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
from .forms import UserForm, UserFormLogin, UserFormRegister, ProfileForm, ProjectSelectionForm, FilterDetailForm, FilterFormSet
from PIL import Image
from .tokens import account_activation_token

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
    template_name = 'website/login.html'
    def get(self, request):
        form = self.form_class(None)
        return render(request, self.template_name, { 'form' : form })

    def post(self, request):
        form = self.form_class(request.POST)
        if form.is_valid():
            user = form.save(commit=False)
            user.is_active = False
            user.save()
            current_site = get_current_site(request)
            message = render_to_string('website/account_activation_email.html', {
                'user' : user,
                'domain' : current_site.domain,
                'uid' : urlsafe_base64_encode(force_bytes(user.pk)).decode(),
                'token' : account_activation_token.make_token(user),
            })
            to_email = form.cleaned_data.get('email')
            email = EmailMessage('Activate your account on PAClab', message, to=[to_email])
            email.send()
            messages.info(request, 'Please check and confirm your email to complete registration.')
            return redirect('website:index')
        return render(request, self.template_name, { 'form' : form })

def activate_account(request, uidb64, token):
    try:
        uid = urlsafe_base64_decode(uidb64).decode()
        user = User.objects.get(pk=uid)
    except(TypeError, ValueError, OverflowError, User.DoesNotExist):
        user = None
    if user is not None and  account_activation_token.check_token(user, token):
        user.profile.active_email = True
        user.profile.save()
        login(request, user)
        messages.success(request, 'Your email is confirmed and account activated!')
    else:
        messages.error(request, 'Invalid activation link!')
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
            return redirect('website:editProfile')
        else:
            messages.warning(request, 'Invalid form entry')
    else:    
        userForm = UserForm(instance=request.user)
        profileForm = ProfileForm(instance=request.user.profile)
    return render(request, 'website/editprofile.html', { 'userForm' : userForm, 'profileForm' : profileForm, 'min_width' : settings.THUMBNAIL_SIZE, 'min_height' : settings.THUMBNAIL_SIZE })

def project_selection(request):
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
        messages.warning(request, ('Invalid form entry'))
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
