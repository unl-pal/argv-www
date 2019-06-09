from django.db import models

class BackendManager(models.Manager):
    def get_by_natural_key(self, name):
        return self.get(name=name)

class Backend(models.Model):
    name = models.CharField(max_length=255)
    enabled = models.BooleanField(default=False)

    objects = BackendManager()

    class Meta:
        unique_together = [['name']]

    def __str__(self):
        return self.name

    def natural_key(self):
        return (self.name,)
