from django.contrib.auth.models import User
from django import forms
from django.forms import modelformset_factory
from .models import Profile, ProjectSelector, Filter, FilterDetail
from .validators import validate_file_size

class UserForm(forms.ModelForm):
    class Meta:
        model = User
        fields = ['first_name', 'last_name']

class UserFormLogin(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput)
    class Meta:
        model = User
        fields = ['username', 'password']

class UserFormRegister(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput)
    class Meta:
        model = User
        fields = ['username', 'email', 'password', 'first_name', 'last_name']

class ProfileForm(forms.ModelForm):
    photo = forms.ImageField(validators=[validate_file_size])
    class Meta:
        model = Profile
        fields = ['photo', 'bio', 'token']
        labels = {
            'photo' : 'Photo',
            'bio' : 'Bio',
            'token' : 'Github Personal Access Token'
        }

class ProjectSelectionForm(forms.ModelForm):
    pfilter = forms.ModelMultipleChoiceField(Filter.objects.all())
    class Meta:
        model = ProjectSelector
        fields = ['input_dataset', 'input_selection', 'output_selection', 'user']

class FilterDetailForm(forms.ModelForm):
    project_selector = forms.IntegerField(widget=forms.HiddenInput())
    class Meta:
        model = FilterDetail
        fields = ['project_selector', 'pfilter']
