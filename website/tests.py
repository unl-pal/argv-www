from django.test import TestCase
from django.contrib.auth.models import User
from backend.models import Backend
from decouple import config
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
        selector = ProjectSelector.objects.create(input_dataset=dataset, user=user)
        selection = Selection.objects.create(project=project, project_selector=selector)
        FilterDetail.objects.create(project_selector=selector, pfilter=flter, value=1, status=READY)
        transformer = ProjectTransformer.objects.create(input_selection=selection, user=user)
        Transform.objects.create(name='testtransform')
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
    
    def test_user(self):
        assert self.user
    
    def test_profile(self):
        assert self.profile
        assert self.profile.user == self.user
    
    def test_paper(self):
        assert self.paper
    
    def test_dataset(self):
        assert self.dataset
    
    def test_filter(self):
        assert self.flter
    
    def test_project(self):
        assert self.project
    
    def test_selector(self):
        assert self.selector
    
    def test_selection(self):
        assert self.selection
    
    def test_filter_detail(self):
        assert self.flter_detail
    
    def test_transformer(self):
        assert self.transformer
    
    def test_transform(self):
        assert self.transformed
    
    def test_analysis(self):
        assert self.analysis

class TestViews(TestModels):    
    def setUp(self):
        self.user = User.objects.last()
        self.profile = self.user.profile
        self.profile.age_confirmation = True
        self.profile.privacy_agreement = True
        self.profile.terms_agreement = True
        self.profile.active_email = True
        self.profile.token = config('GITHUB_TEST_TOKEN')
        self.profile.save()
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
        self.client.login(username='testuser', password='testing321')

    def test_index(self):
        res = self.client.get('')
        assert res.status_code == 200
    
    def test_funding(self):
        res = self.client.get('/funding/')
        assert res.status_code == 200

    def test_papers(self):
        res = self.client.get('/papers/')
        assert res.status_code == 200

    def test_people(self):
        res = self.client.get('/people/')
        assert res.status_code == 200

    def test_login_get(self):
        res = self.client.get('/login/')
        assert res.status_code == 200
    
    def test_login_post(self):
        data = {
            'username': 'testuser',
            'password': 'testing321'
        }
        res = self.client.post('/login/')
        assert res.status_code == 200

    def test_logout(self):
        res = self.client.post('/logout/')
        assert res.status_code == 302

    def test_register_get(self):
        res = self.client.get('/register/')
        assert res.status_code == 200
    
    # test register manually because of github token

    def test_profile_get(self):
        res = self.client.get('/editprofile/')
        assert res.status_code == 200

    # test edit profile manually because of image upload

    # test password reset and email verification manually because of email

    def test_privacy_policy(self):
        res = self.client.get('/policies/privacy/')
        assert res.status_code == 200

    def test_terms_of_user(self):
        res = self.client.get('/policies/terms_of_use/')
        assert res.status_code == 200

    def test_new_project_selection(self):
        res = self.client.get('/project/selection/')
        assert res.status_code == 200
    
    # test post logic manually for custom code

    def test_project_list(self):
        res = self.client.get('/project/list/')
        assert res.status_code == 200

    def test_project_detail(self):
        res = self.client.get(f'/project/detail/{self.selector.slug}/')
        assert res.status_code == 200

    def test_project_delete_get(self):
        res = self.client.get(f'/project/delete/{self.selector.slug}/')
        assert res.status_code == 200
    
    def test_project_delete_post(self):
        res = self.client.post(f'/project/delete/{self.selector.slug}/')
        assert res.status_code == 302
