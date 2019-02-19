from django.contrib.auth.models import User
from django import forms
from django.forms import formset_factory
from .models import Profile, ProjectSelector, Filter

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
    class Meta:
        class PhotoInput(forms.widgets.ClearableFileInput):
            template_name = 'website/photoinput.html'

        model = Profile
        fields = ['photo', 'bio', 'token', 'sharetoken']
        labels = {
            'bio' : 'Biography',
            'token' : 'Github Personal Access Token',
            'sharetoken' : 'Allow using token for system jobs'
        }
        widgets = {
            'token': forms.TextInput(attrs={'size': 40}),
            'photo': PhotoInput(),
        }

class ProjectSelectionForm(forms.ModelForm):
    class Meta:
        model = ProjectSelector
        fields = ['input_dataset']

class FilterDetailForm(forms.Form):
    pfilter = forms.ModelChoiceField(Filter.objects.all())
    value = forms.CharField(
        max_length=1000,
        widget=forms.TextInput(attrs={
            'placeholder' : 'Enter value'
        }),
        required=True)

FilterFormSet = formset_factory(FilterDetailForm)
