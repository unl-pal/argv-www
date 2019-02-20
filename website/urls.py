from django.urls import path, re_path
from django.urls import reverse_lazy
from django.contrib.auth import views as auth_views
from django.views.generic import TemplateView
from . import views

app_name = 'website'

urlpatterns = [
    path('', TemplateView.as_view(template_name='website/index.html'), name='index'),
    path('funding/', TemplateView.as_view(template_name='website/funding.html'), name='funding'),
    path('papers/', views.PapersView.as_view(), name='papers'),
    path('people/', views.PeopleView.as_view(), name='people'),
    path('login/', views.LoginView.as_view(), name='login'),
    path('logout/', views.logoutView, name='logout'),
    path('register/', views.RegisterView.as_view(), name='register'),
    re_path(r'^activate/(?P<uidb64>[0-9A-Za-z_\-]+)/(?P<token>[0-9A-Za-z]{1,13}-[0-9A-Za-z]{1,20})/$', views.activate_account, name='activate_account'),
    path('editprofile/', views.profile, name='editProfile'),
    path('ajax/data_default/', views.data_default, name='data_default'),
    path('project/selection/', views.project_selection, name='project_selection'),
    path('password/reset/', auth_views.PasswordResetView.as_view(email_template_name='website/reset/password_reset_email.html', success_url=reverse_lazy('website:password_reset_done'), template_name='website/reset/passwordReset.html'), name='password_reset'),
    path('password/reset/done/', auth_views.PasswordResetDoneView.as_view(template_name='website/reset/passwordResetDone.html'), name='password_reset_done'),
    path('password/reset/confirm/<uidb64>/<token>/', auth_views.PasswordResetConfirmView.as_view(success_url=reverse_lazy('website:password_reset_complete'), template_name='website/reset/passwordResetConfirm.html'), name='password_reset_confirm'),
    path('password/reset/complete/', auth_views.PasswordResetCompleteView.as_view(template_name='website/reset/passwordResetComplete.html'), name='password_reset_complete'),
]
