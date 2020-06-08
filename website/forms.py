from django import forms
from django.contrib.auth.forms import PasswordChangeForm, UserCreationForm
from django.contrib.auth.models import User
from django.core.exceptions import ValidationError
from django.core.mail import EmailMultiAlternatives
from django.core.validators import validate_email
from django.forms import BaseFormSet, formset_factory
from django.template.loader import get_template
from django.utils.safestring import mark_safe
from django_countries.fields import CountryField

from .models import (BackendFilter, Profile, ProjectSelector, Transform,
                     TransformOption)


class UserRegisterForm(UserCreationForm):
    country = CountryField().formfield(required=True)
    terms_agreement = forms.BooleanField(label=mark_safe('I agree to the <a role="button" data-toggle="modal" data-target="#termsModal">Terms of Use</a>'), required=True)
    privacy_agreement = forms.BooleanField(label=mark_safe('I agree to the <a role="button" data-toggle="modal" data-target="#privacyModal">Privacy Policy</a>'), required=True)
    age_confirmation = forms.BooleanField(label='I am 13 years or older', required=True)

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.fields['email'].required = True

    class Meta:
        model = User
        fields = ['username', 'email', 'password1', 'password2', 'country', 'terms_agreement', 'privacy_agreement', 'age_confirmation']

class UserLoginForm(forms.Form):
    username = forms.CharField(widget=forms.TextInput(attrs={'placeholder': 'Username', 'autofocus': 'autofocus'}))
    password = forms.CharField(widget=forms.PasswordInput(attrs={'placeholder': 'Password'}))
    fields = ['username', 'password']

class UserPasswordForm(PasswordChangeForm):
    class Meta:
        model = User
        fields = '__all__'

class EmailShareForm(forms.Form):
    email = forms.CharField(label='')
    valid_emails = set()

    def clean(self):
        cleaned_data = super().clean()

        for user in cleaned_data['email'].split(','):
            if '@' in user:
                try:
                    validate_email(user)
                    self.valid_emails.add(user)
                except ValidationError:
                    self.add_error('email', 'Invalid email address: ' + user)
            else:
                if '(' in user and ')' in user:
                    try:
                        name, username = user.split('(')
                        username = username.rstrip(')')
                        self.valid_emails.add(str(User.objects.get(username=username).email))
                    except:
                        self.add_error('email', 'Invalid user: ' + user)
                else:
                    self.add_error('email', 'Invalid user: ' + user)

        return cleaned_data

    def save(self, request, kind, slug):
        user = str(request.user.username)
        url = request.build_absolute_uri('/' + kind + '/' + slug + '/')
        text_content = user + ' has shared a project ' + kind + ' with you on PAClab: ' + url
        html_content = get_template('website/' + kind + 's/shared_email.html').render({'user' : user, 'url' : url})
        msg = EmailMultiAlternatives('PAClab Project ' + kind, text_content, request.user.email, list(self.valid_emails))
        msg.attach_alternative(html_content, "text/html")
        msg.send()

class UserForm(forms.ModelForm):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.fields['email'].required = True

    class Meta:
        model = User
        fields = ['email', ]

class StaffUserForm(UserForm):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.fields['first_name'].required = True
        self.fields['last_name'].required = True

    class Meta(UserForm.Meta):
        UserForm.Meta.fields.append('first_name')
        UserForm.Meta.fields.append('last_name')

class ProfileForm(forms.ModelForm):
    class Meta:
        class PhotoInput(forms.widgets.ClearableFileInput):
            template_name = 'website/user/photoinput.html'

        model = Profile
        fields = ['country', 'photo', 'token', 'sharetoken', ]
        labels = {
            'token' : 'Github Personal Access Token',
            'sharetoken' : 'Allow using token for system jobs',
        }
        widgets = {
            'token' : forms.TextInput(attrs={'size': 40}),
            'photo' : PhotoInput(),
        }

class StaffProfileForm(ProfileForm):
    class Meta(ProfileForm.Meta):
        ProfileForm.Meta.fields.insert(1, 'bio')
        ProfileForm.Meta.labels['bio'] = 'Biography'

class ProjectSelectionForm(forms.ModelForm):
    class Meta:
        model = ProjectSelector
        fields = ['input_dataset', 'parent']
        widgets = {
            'parent': forms.HiddenInput(),
        }

class FilterDetailForm(forms.Form):
    pfilter = forms.ModelChoiceField(BackendFilter.objects.filter(enabled=True).order_by('flter__name'))
    value = forms.CharField(
        max_length=1000,
        widget=forms.TextInput(attrs={
            'placeholder' : 'Enter value',
            'size' : 10
        }),
        required=True)

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        if self.is_valid() and 'pfilter' in self.cleaned_data:
            self.fields['pfilter'].help_text = mark_safe(self.cleaned_data['pfilter'].flter.help_text)

class BaseFilterFormSet(BaseFormSet):
    def clean(self):
        cleaned_data = super().clean()

        filters = []
        for form in self.forms:
            if form.is_valid() and 'pfilter' in form.cleaned_data:
                flter = form.cleaned_data['pfilter']
                if flter.pk not in filters:
                    filters.append(flter.pk)
                else:
                    form.add_error('pfilter', "Filter used multiple times. Filters may only be used once.")

        return cleaned_data

FilterFormSet = formset_factory(FilterDetailForm, formset=BaseFilterFormSet, min_num=1, extra=0)

class TransformOptionForm(forms.Form):
    transform = forms.ModelChoiceField(Transform.objects.filter(enabled=True).order_by('name'))
