from django.http import HttpResponse
from django.shortcuts import render, redirect, get_object_or_404
from django.views.generic import TemplateView, ListView, DetailView, View
from django.views.generic.edit import UpdateView, CreateView, DeleteView
from django.contrib import messages
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User
from django.contrib.auth.decorators import login_required
from django.db import transaction
from .models import Paper, Profile
from .forms import UserForm, UserFormLogin, UserFormRegister, ProfileForm

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
            messages.warning(request, ('Invalid form entry.'))
            return redirect('website:editProfile')
        return redirect('website:login')
