from django.contrib.auth.models import User
from django import forms
from django.forms import formset_factory
from .models import Profile, ProjectSelector, Filter, FilterDetail
from .validators import validate_file_size

class UserForm(forms.ModelForm):
    def __init__(self, *args, **kwargs):
        super(UserForm, self).__init__(*args, **kwargs)
        self.fields['first_name'].required = True
        self.fields['last_name'].required = True
        self.fields['email'].required = True

    class Meta:
        model = User
        fields = ['first_name', 'last_name', 'email']

class UserFormLogin(forms.Form):
    username = forms.CharField(widget=forms.TextInput(attrs={'placeholder': 'Username', 'autofocus': 'autofocus'}))
    password = forms.CharField(widget=forms.PasswordInput(attrs={'placeholder': 'Password'}))
    fields = ['username', 'password']

class UserFormRegister(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput)

    def __init__(self, *args, **kwargs):
        super(UserFormRegister, self).__init__(*args, **kwargs)
        self.fields['first_name'].required = True
        self.fields['last_name'].required = True
        self.fields['email'].required = True

    class Meta:
        model = User
        fields = ['username', 'email', 'password', 'first_name', 'last_name']

class ProfileForm(forms.ModelForm):
    photo = forms.ImageField(validators=[validate_file_size])

    class Meta:
        model = Profile
        fields = ['photo', 'bio', 'token', 'sharetoken']
        labels = {
            'photo' : 'Photo',
            'bio' : 'Bio',
            'token' : 'Github Personal Access Token',
            'sharetoken' : 'Allow using token for system jobs'
        }
        widgets = { 'token': forms.TextInput(attrs={'size': 40})}

class ProjectSelectionForm(forms.ModelForm):
    # pfilter = forms.ModelMultipleChoiceField(Filter.objects.all())
    class Meta:
        model = ProjectSelector
        fields = ['input_dataset']

class FilterDetailForm(forms.Form):
    pfilter = forms.ModelChoiceField(Filter.objects.all())
    value = forms.CharField(
        max_length=1000,
        widget=forms.TextInput(attrs={
            'class' : 'form-control',
            'placeholder' : 'Enter value'
        }),
        required=False)

FilterFormSet = formset_factory(FilterDetailForm)
