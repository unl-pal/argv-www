from django.apps import AppConfig

class WebsiteConfig(AppConfig):
    name = 'website'
    default_auto_field = 'django.db.models.AutoField'

    def ready(self):
        import website.signals
