from django.conf import settings
from django.contrib.auth.models import User
from django import forms
from PIL import Image
from .models import Profile
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
    x = forms.FloatField(required=False, widget=forms.HiddenInput())
    y = forms.FloatField(required=False, widget=forms.HiddenInput())
    width = forms.FloatField(required=False, widget=forms.HiddenInput())
    height = forms.FloatField(required=False, widget=forms.HiddenInput())
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

    def save(self):
        profile = super(ProfileForm, self).save()

        x = self.cleaned_data.get('x')
        y = self.cleaned_data.get('y')
        w = self.cleaned_data.get('width')
        h = self.cleaned_data.get('height')

        if x and y and w and h:
            image = Image.open(profile.photo)
            cropped_image = image.crop((x, y, w + x, h + y))
            resized_image = cropped_image.resize(settings.THUMBNAIL_SIZE, Image.LANCZOS)
            resized_image.save(profile.photo.path)

        return profile
