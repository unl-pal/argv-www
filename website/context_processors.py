from django.conf import settings

def adminConstant(self):
    return { 'ADMIN' : 'Admin' }

def moderatorConstant(self):
    return { 'MODERATOR' : 'Moderator' }

def retiredConstant(self):
    return { 'RETIRED' : 'Retired' }

def hijackConstant(self):
    return { 'USE_HIJACK' : settings.USE_HIJACK }
