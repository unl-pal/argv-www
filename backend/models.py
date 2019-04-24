from django.db import models

class Backend(models.Model):
    name = models.CharField(max_length=255)
    enable = models.BooleanField(default=False)

    def __str__(self):
        return self.name

class SupportedFilter(models.Model):
    name = models.CharField(max_length=200, default='')
    INT = 'Integer'
    STRING = 'String'
    LIST = 'List'
    TYPE_CHOICES = (
        (INT, 'Integer'),
        (STRING, 'String'),
        (LIST, 'List'),
    )
    val_type = models.CharField(max_length=10, choices=TYPE_CHOICES, default=INT)
    value = models.CharField(max_length=100, default='Enter value here')

    def __str__(self):
        return self.name
