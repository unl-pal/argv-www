import requests
import re
from django.core.exceptions import ValidationError
from django.conf import settings

def validate_file_size(value):
    filesize = value.size
    
    if filesize > settings.MAX_FILE_UPLOAD:
        raise ValidationError('The maximum file size for profile photos is ' + str(settings.MAX_FILE_UPLOAD))
    return value

def validate_gh_token(token):
    if len(re.match('[a-fA-F0-9]+', token).group(0)) != 40:
        raise ValidationError('This is not a valid GitHub Personal Token.')
    response = requests.get('https://api.github.com/rate_limit', headers={'Authorization': 'token ' + token})
    if 'message' in response.json():
        raise ValidationError('The GitHub Personal Token provided could not be verified with the GitHub API.')
    return token
