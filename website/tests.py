from django.test import TestCase
from django.contrib.auth.models import User
from backend.models import Backend
from .models import Paper, Profile, Dataset, Filter, FilterDetail, Project, ProjectSelector, ProjectTransformer, Transform, Selection, Analysis, UserAuthAuditEntry
from .choices import *

class TestModels(TestCase):
    @classmethod
    def setUpTestData(cls):
        user = User.objects.create_superuser(username='testuser', email='testuser@email.com', password='testing321')
        Paper.objects.create(author='testauthor', title='testtitle', publish='testpublisher', link='http://www.testlink.com/')
        dataset = Dataset.objects.create(name='testdataset')
        backend = Backend.objects.create(name='testbackend')
        flter = Filter.objects.create(name='testfilter', val_type='Integer', default_val=1, associated_backend=backend)
        project = Project.objects.create(dataset=dataset, url='http://www.google.com/')
        selector = ProjectSelecotr.objects.create(input_dataset=dataset, user=user, pfilter=flter, processed=READY)
        Selection.objects.create(project=project, project_selector=selector)
        FilterDetail.objects.create(project_selector=selector, pfilter=flter, value=1, status=READY)
        transformer = ProjectTransformer.objects.create(input_selection=selection, user=user)
        Transform.objects.create(project_transformers=transformer, name='testtransform')
        Analysis.objects.create(input_selection=selection, user=user)
    
    def setUp(self):
        self.user = User.objects.last()
        self.profile = self.user.profile
        self.paper = Paper.objects.last()
        self.dataset = Dataset.objects.last()
        self.flter = Filter.objects.last()
        self.project = Project.objects.last()
        self.selector = ProjectSelector.objects.last()
        self.selection = Selection.objects.last()
        self.flter_detail = FilterDetail.objects.last()
        self.transformer = ProjectTransformer.objects.last()
        self.transformed = Transform.objects.last()
        self.analysis = Analysis.objects.last()
