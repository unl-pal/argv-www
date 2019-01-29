from django.http import HttpResponse
from django.shortcuts import render, redirect, get_object_or_404
from django.views.generic import TemplateView, ListView, DetailView, View
from django.views.generic.edit import UpdateView, CreateView, DeleteView
from django.contrib import messages
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User
from django.contrib.auth.mixins import LoginRequiredMixin
from django.contrib.auth.decorators import login_required
from django.db import transaction
from django.conf import settings
from .models import Paper, Profile, FilterDetail, ProjectSelector, Filter
from .forms import UserForm, UserFormLogin, UserFormRegister, ProfileForm, ProjectSelectionForm, FilterDetailForm, FilterFormSet

# Create your views here.
class IndexView(TemplateView):
    template_name="website/index.html"

class FundingView(TemplateView):
    template_name="website/funding.html"

class PapersView(ListView):
    template_name="website/papers.html"
    context_object_name='allPapers'
    def get_queryset(self):
        return Paper.objects.all()

class PaperDetails(DetailView):
    template_name="website/paperDetails.html"
    model = Paper

class PeopleView(ListView):
    template_name="website/people.html"
    context_object_name = 'allPeople'
    def get_queryset(self):
        return User.objects.all().order_by('last_name')

class RegisterView(View):
    form_class = UserFormRegister
    template_name = "website/login.html"
    def get(self, request):
        form = self.form_class(None)
        return render(request, self.template_name, { 'form' : form })

    def post(self, request):
        form = self.form_class(request.POST)
        if form.is_valid():
            user = form.save(commit=False)
            username = form.cleaned_data['username']
            password = form.cleaned_data['password']
            user.set_password(password)
            user.save()
            user = authenticate(username=username, password=password)
            if user is not None:
                if user.is_active:
                    login(request, user)
                    return redirect('website:index')
            return render(request, self.template_name, { 'form' : form })

class LoginView(View):
    form_class = UserFormLogin
    template_name = "website/login.html"

    def get(self, request):
        form = self.form_class(None)
        return render(request, self.template_name, { 'form' : form })

    def post(self, request):
        form = self.form_class(request.POST)
        username = request.POST['username']
        password = request.POST['password']
        user = authenticate(username=username, password=password)
        if user is not None:
            if user.is_active:
                login(request, user)
                return redirect('website:index')
        return render(request, self.template_name, { 'form' : form })

def logoutView(request):
    logout(request)
    return redirect('website:index')

class EditProfile(View):
    userForm = UserForm
    profileForm = ProfileForm
    template_name = "website/editprofile.html"

    def get(self, request):
        if request.user.is_authenticated:
            userForm = self.userForm(instance=request.user)
            profileForm = self.profileForm(instance=request.user.profile)
            return render(request, self.template_name, { 'userForm' : userForm, 'profileForm' : profileForm })
        else:
            return redirect('website:login')

    def post(self, request):
        if request.user.is_authenticated:
            userForm = self.userForm(request.POST, instance=request.user)
            profileForm = self.profileForm(request.POST, request.FILES, instance=request.user)
            if userForm.is_valid() and profileForm.is_valid():
                user = User.objects.get(pk=request.user.pk)
                profile = user.profile
                first_name = userForm.cleaned_data['first_name']
                last_name = userForm.cleaned_data['last_name']
                photo = profileForm.cleaned_data['photo']
                bio = profileForm.cleaned_data['bio']
                user.first_name = first_name
                user.last_name = last_name
                if len(request.FILES) != 0:
                    profile.photo = photo
                else:
                    profile.photo = profile.photo
                profile.bio = bio
                profile.save()
                user.save()
                messages.success(request, ('Your profile was successfully updated!'))
                return redirect('website:editProfile')
            else:
                messages.warning(request, (profileForm.errors))
        return render(request, self.template_name, { 'userForm' : userForm, 'profileForm' : profileForm })

class ProjectSelection(LoginRequiredMixin, View):
    template_name = 'website/projectSelection.html'
    p_form = ProjectSelectionForm
    f_form = FilterFormSet

    def get(self, request):
        p_form = self.p_form()
        f_form = self.f_form()
        return render(request, self.template_name, { 'p_form' : p_form, 'f_form' : f_form })

    def post(self, request):
        p_form = self.p_form(request.POST)
        f_form = self.f_form(request.POST)
        if p_form.is_valid() and f_form.is_valid():
            selector = ProjectSelector()
            selector.user = request.user
            selector.input_dataset = p_form.cleaned_data['input_dataset']
            selector.input_selection = p_form.cleaned_data['input_selection']
            selector.output_selection = p_form.cleaned_data['output_selection']
            selector.save()
            for form in f_form:
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
        else:
            messages.warning(request, ('Invalid Form Entry'))
            return render(request, self.template_name, { 'p_form' : p_form })
