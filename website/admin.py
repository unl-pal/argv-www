from django.contrib import admin
from .models import Paper, Profile

class ProfileAdmin(admin.ModelAdmin):
    model = Profile
    list_display = ['get_username', 'get_email', 'get_firstname', 'get_lastname', ]
    search_fields = ['user__first_name', 'user__last_name', 'user__email', ]

    def get_username(self, obj):
        return obj.user.username
    get_username.admin_order_field  = 'user__username'
    get_username.short_description = 'Username'

    def get_firstname(self, obj):
        return obj.user.first_name
    get_firstname.admin_order_field  = 'user__first_name'
    get_firstname.short_description = 'First Name'

    def get_lastname(self, obj):
        return obj.user.last_name
    get_lastname.admin_order_field  = 'user__last_name'
    get_lastname.short_description = 'Last Name'

    def get_email(self, obj):
        return obj.user.email
    get_email.admin_order_field  = 'user__email'
    get_email.short_description = 'Email Address'

# Register your models here.
admin.site.register(Paper)
admin.site.register(Profile, ProfileAdmin)
