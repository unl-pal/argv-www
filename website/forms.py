from django.contrib.auth.models import User
from django import forms
from django.forms import modelformset_factory
from .models import Profile, Dataset, Filter
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

class DatasetForm(forms.ModelForm):
    #What is the final model we are posting our results to?
    dataset = forms.ModelChoiceField(queryset=Dataset.objects.all())
    class Meta:
        model = Dataset
        fields = []

FilterFormset = modelformset_factory(
    Filter,
    fields=['name'],
    extra=1,
)
