from django.http import HttpResponse, JsonResponse
from django.shortcuts import render, redirect, get_object_or_404
from django.views.generic import ListView, DetailView, View
from django.views.generic.edit import UpdateView, CreateView, DeleteView
from django.contrib import messages
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User
from django.contrib.auth.decorators import login_required
from django.contrib.auth.mixins import LoginRequiredMixin
from django.db import transaction
from django.conf import settings
from .mixins import EmailRequiredMixin
from .models import Paper, Profile, FilterDetail, ProjectSelector, Filter
from .forms import UserForm, UserPasswordForm, UserFormLogin, UserFormRegister, ProfileForm, ProjectSelectionForm, FilterDetailForm, FilterFormSet
from PIL import Image
from .models import Paper, Profile
from .forms import UserForm, UserFormLogin, UserFormRegister, ProfileForm
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
    template_name = 'website/login.html'
    def get(self, request):
        form = self.form_class(None)
        return render(request, self.template_name, { 'form' : form })

    def post(self, request):
        form = self.form_class(request.POST)
        if form.is_valid():
            user = form.save()
            messages.success(request, 'Form saved!')
            login(request, user)
            return redirect('website:index')
        messages.warning(request, 'Invalid form entry')
        return render(request, self.template_name, { 'form' : form })

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

class ProjectListView(EmailRequiredMixin, ListView):
    template_name='website/projects.html'
    context_object_name='projects'
    def get_queryset(self):
        return ProjectSelector.objects.all().filter(user=self.request.user)

class ProjectDetailView(LoginRequiredMixin, DetailView):
    template_name = 'website/projectsDetail.html'
    context_object_name = 'project'
    model = ProjectSelector

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

@login_required
def password_change(request):
    if request.method == 'POST':
        form = UserPasswordForm(request.user, request.POST)
        if form.is_valid():
            form.save()
            messages.success(request, 'Password updated!')
            return redirect('website:index')
        messages.warning(request, 'Invalid form entry')
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
        return redirect('website:editProfile')

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
            selector.gen_slug()
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
