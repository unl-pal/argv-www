import re

from django.conf import settings
from django.core.exceptions import ValidationError
import requests
from django.core.validators import URLValidator

def validate_file_size(value):
    filesize = value.size

    if filesize > settings.MAX_FILE_UPLOAD:
        raise ValidationError('The maximum file size for profile photos is ' + str(settings.MAX_FILE_UPLOAD))
    return value

def validate_gh_token(token):
    m = re.match('[a-fA-F0-9]+', token)
    if not m or len(m.group(0)) != 40:
        raise ValidationError('This is not a valid GitHub Personal Token.')
    response = requests.get('https://api.github.com/rate_limit', headers={'Authorization': 'token ' + token})
    if 'message' in response.json():
        raise ValidationError('The GitHub Personal Token provided could not be verified with the GitHub API.')
    return token

def string_to_urls(value):
    urls = []
    for l in value.strip().split('\r\n'):
        l = l.strip()
        if len(l) > 0:
            urls.append(l.strip())
    return urls

def validate_urls(value):
    validate = URLValidator(schemes=['http', 'https'])

    for url in string_to_urls(value):
        if not (url.lower().startswith('https://') or url.lower().startswith('http://')):
            raise ValidationError('The URL "' + url + '" must use http:// or https://.')

        try:
            validate(url)
        except ValidationError:
            raise ValidationError('The line "' + url + '" is not a valid URL.')

    return value
