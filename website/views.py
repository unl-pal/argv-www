from django.http import HttpResponse
from django.shortcuts import render, redirect, get_object_or_404
from django.views.generic import TemplateView, ListView, DetailView, View
from django.views.generic.edit import UpdateView, CreateView, DeleteView
from django.contrib import messages
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User
from django.contrib.auth.decorators import login_required
from django.db import transaction
from django.conf import settings
from .models import Paper, Profile
from .forms import UserForm, UserFormLogin, UserFormRegister, ProfileForm

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
        if form.is_valid():
            username = form.cleaned_data['username']
            password = form.cleaned_data['password']
            user = authenticate(username=username, password=password)
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
            messages.success(request, 'Profile successfully updated')
        else:
            messages.warning(request, 'Invalid form entry')
    else:    
        userForm = UserForm(instance=request.user)
        profileForm = ProfileForm(instance=request.user.profile)
    return render(request, 'website/editprofile.html', { 'userForm' : userForm, 'profileForm' : profileForm, 'min_width' : settings.THUMBNAIL_SIZE, 'min_height' : settings.THUMBNAIL_SIZE })
