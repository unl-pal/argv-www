from django.urls import path
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
    path('editprofile/', views.profile, name='edit_profile'),
    path('verify/email/', views.verify_email_link, name='verify_email_link'),
    path('verify/<uidb64>/<token>/', views.verify_email, name='verify_email'),
    path('policies/privacy/', TemplateView.as_view(template_name='website/privacy.html'), name='privacy_policy'),
    path('policies/terms_of_use/', TemplateView.as_view(template_name='website/terms.html'), name='tos'),
    path('api/filter_default/', views.api_filter_default, name='api_filter_default'),
    path('api/usernames/', views.api_usernames, name='api_usernames'),
    path('selection/', views.project_selection, name='project_selection'),
    path('selection/list/', views.ProjectListView.as_view(), name='project_list'),
    path('selection/detail/<slug>/', views.project_detail, name='project_detail'),
    path('selection/delete/<slug>/', views.project_delete, name='project_delete'),
    path('selection/download/<slug>/', views.download, name='download'),
    path('password/change/', views.password_change, name='password_change'),
    path('password/reset/', auth_views.PasswordResetView.as_view(email_template_name='website/reset/password_reset_email.html', success_url=reverse_lazy('website:password_reset_done'), template_name='website/reset/passwordReset.html'), name='password_reset'),
    path('password/reset/done/', auth_views.PasswordResetDoneView.as_view(template_name='website/reset/passwordResetDone.html'), name='password_reset_done'),
    path('password/reset/confirm/<uidb64>/<token>/', auth_views.PasswordResetConfirmView.as_view(success_url=reverse_lazy('website:password_reset_complete'), template_name='website/reset/passwordResetConfirm.html'), name='password_reset_confirm'),
    path('password/reset/complete/', auth_views.PasswordResetCompleteView.as_view(template_name='website/reset/passwordResetComplete.html'), name='password_reset_complete'),
]
