# Processed
PROCESSED = 'PROCESSED'
ONGOING = 'ONGOING'
READY = 'READY'
PROCESS_STATUS = (
    (PROCESSED, 'Processed'),
    (ONGOING, 'Ongoing'),
    (READY, 'Ready'),
)

# Honorific
NONE = ''
DOCTOR = 'Dr.'
HONORIFIC_CHOICES = (
    (NONE, ''),
    (DOCTOR, 'DR'),
)

# Staff Status
USER = 'User'
RETIRED = 'Retired'
MODERATOR = 'Moderator'
ADMIN = 'Admin'
STAFF_STATUS = (
    (USER, 'USER'),
    (RETIRED, 'RETIRED'),
    (MODERATOR, 'MODERATOR'),
    (ADMIN, 'ADMIN'),
)

# Types
INT = 'Integer'
STRING = 'String'
LIST = 'List'
TYPE_CHOICES = (
    (INT, 'Integer'),
    (STRING, 'String'),
    (LIST, 'List'),
)

MANUAL_DATASET = 'Manual Selections'