from django.db import models

class Backend(models.Model):
    name = models.CharField(max_length=255)
    enable = models.BooleanField(default=False)

    def __str__(self):
        return self.name
