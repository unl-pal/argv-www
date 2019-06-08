from django.contrib.auth.models import User
from django.contrib.auth.forms import UserCreationForm, PasswordChangeForm
from django.utils.safestring import mark_safe
from django import forms
from django.forms import formset_factory
from .models import Profile, ProjectSelector, Filter
from .validators import validate_gh_token

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

class UserPasswordForm(PasswordChangeForm):
    class Meta:
        model = User
        fields = '__all__'

class UserFormRegister(UserCreationForm):
    token = forms.CharField(max_length=40, validators=[validate_gh_token], help_text='GitHub Access Token', required=True)
    terms_agreement = forms.BooleanField(label=mark_safe('I agree to the <a role="button" data-toggle="modal" data-target="#termsModal">Terms of Use</a>'))
    privacy_agreement = forms.BooleanField(label=mark_safe('I agree to the <a role="button" data-toggle="modal" data-target="#privacyModal">Privacy Policy</a>'))
    age_confirmation = forms.BooleanField(label='I am 13 years or older')

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.fields['email'].required = True
        self.fields['first_name'].required = True
        self.fields['last_name'].required = True

    class Meta:
        model = User
        fields = ['username', 'email', 'first_name', 'last_name', 'password1', 'password2', 'token', 'terms_agreement', 'privacy_agreement', 'age_confirmation']

class ProfileForm(forms.ModelForm):
    class Meta:
        class PhotoInput(forms.widgets.ClearableFileInput):
            template_name = 'website/photoinput.html'

        model = Profile
        fields = ['photo', 'token', 'sharetoken']
        labels = {
            'token' : 'Github Personal Access Token',
            'sharetoken' : 'Allow using token for system jobs',
        }
        widgets = {
            'token' : forms.TextInput(attrs={'size': 40}),
            'photo' : PhotoInput(),
        }

class ProjectSelectionForm(forms.ModelForm):
    class Meta:
        model = ProjectSelector
        fields = ['input_dataset']
        labels = {
            'input_dataset' : '',
        }

class EmailForm(forms.Form):
    email = forms.CharField(label='')

class FilterDetailForm(forms.Form):
    pfilter = forms.ModelChoiceField(Filter.objects.all().filter(enabled=True))
    value = forms.CharField(
        max_length=1000,
        widget=forms.TextInput(attrs={
            'placeholder' : 'Enter value',
            'size' : 10
        }),
        required=True)

FilterFormSet = formset_factory(FilterDetailForm)
