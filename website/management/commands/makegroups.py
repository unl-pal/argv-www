from django.contrib.auth.models import Group, Permission, User
from django.core.management import BaseCommand

from website import models

GROUPS_PERMISSIONS = {
    'PaperAdmins': {
        models.Paper: ['add', 'change', 'delete', 'view'],
    },
    'UserAdmins': {
        User: ['add', 'change', 'view'],
        models.Profile: ['add', 'change', 'view'],
    },
    'PAClabAdmins': {
        models.Dataset: ['view'],
        models.Project: ['add', 'change', 'view'],
        models.ProjectSelector: ['add', 'change', 'view', 'website:view_disabled_selectors'],
        models.Filter: ['view'],
        models.FilterDetail: ['add', 'change', 'view'],
        models.TransformedProject: ['add', 'change', 'view'],
        models.ProjectTransformer: ['add', 'change', 'view', 'website:view_disabled_transforms'],
        models.Transform: ['view'],
        models.TransformOption: ['add', 'change', 'view'],
    },
}

'''Make groups

Usage: manage.py makegroups
Create default PAClab groups/permissions
'''
class Command(BaseCommand):
    help = 'Create default PAClab groups/permissions'

    def handle(self, *args, **options):
        for group_name in GROUPS_PERMISSIONS:
            group, created = Group.objects.get_or_create(name=group_name)

            for model_cls in GROUPS_PERMISSIONS[group_name]:
                for _, perm_name in enumerate(GROUPS_PERMISSIONS[group_name][model_cls]):
                    if ':' in perm_name:
                        codename = perm_name[perm_name.find(':') + 1:]
                    else:
                        codename = perm_name + '_' + model_cls._meta.model_name

                    try:
                        group.permissions.add(Permission.objects.get(codename=codename))
                        self.stdout.write('Adding ' + codename + ' to group ' + group.__str__())
                    except Permission.DoesNotExist:
                        self.stderr.write(codename + ' not found')

            group.save()
