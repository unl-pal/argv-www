from django.core.exceptions import ValidationError


def validate_file_size(value):
    filesize = value.size
    
    if filesize > 4 * 1024 * 1024:
        raise ValidationError("The maximum file size that can be uploaded is 0")
    else:
        return value