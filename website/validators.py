import requests
from django.core.exceptions import ValidationError
from django.conf import settings

def validate_file_size(value):
    filesize = value.size
    
    if filesize > settings.MAX_FILE_UPLOAD:
        raise ValidationError('The maximum file size for profile photos is ' + str(settings.MAX_FILE_UPLOAD))
    return value

def validate_gh_token(token):
    response = requests.get('https://api.github.com/rate_limit', headers={'Authorization': 'token ' + token})
    if 'message' in response.json():
        raise ValidationError('The GitHub Personal Token provided is invalid')
    return token
