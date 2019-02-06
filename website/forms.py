from django.contrib.auth.models import User
from django import forms
from django.forms import formset_factory
from .models import Profile, ProjectSelector, Filter, FilterDetail
from .validators import validate_file_size

class UserForm(forms.ModelForm):
    class Meta:
        model = User
        fields = ['first_name', 'last_name', 'email']

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
    # pfilter = forms.ModelMultipleChoiceField(Filter.objects.all())
    class Meta:
        model = ProjectSelector
        fields = ['input_dataset', 'input_selection', 'output_selection']

class FilterDetailForm(forms.Form):
    pfilter = forms.ModelChoiceField(Filter.objects.all())
    value = forms.CharField(
        max_length=1000,
        widget=forms.TextInput(attrs={
            'class' : 'form-control',
            'placeholder' : 'Enter value'
        }),
        required=False)

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.fields['value'].widget.attrs['placeholder'] = 'Hello World'

FilterFormSet = formset_factory(FilterDetailForm)
