from django.urls import path
from django.urls import reverse_lazy
from django.contrib.auth import views as auth_views
from . import views

app_name = 'website'

urlpatterns = [
    path('', views.IndexView.as_view(), name='index'),
    path('funding/', views.FundingView.as_view(), name='funding'),
    path('papers/', views.PapersView.as_view(), name='papers'),
    path('people/', views.PeopleView.as_view(), name='people'),
    path('login/', views.LoginView.as_view(), name='login'),
    path('logout/', views.logoutView, name='logout'),
    path('register/', views.RegisterView.as_view(), name='register'),
    path('editprofile/', views.profile, name='editProfile'),
    path('password/change/', views.password_change, name='password_change'),
    path('ajax/data_default/', views.data_default, name='data_default'),
    path('project/selection/', views.project_selection, name='project_selection'),
    path('password/reset/', auth_views.PasswordResetView.as_view(email_template_name='website/reset/password_reset_email.html', success_url=reverse_lazy('website:password_reset_done'), template_name='website/reset/passwordReset.html'), name='password_reset'),
    path('password/reset/done/', auth_views.PasswordResetDoneView.as_view(template_name='website/reset/passwordResetDone.html'), name='password_reset_done'),
    path('password/reset/confirm/<uidb64>/<token>/', auth_views.PasswordResetConfirmView.as_view(success_url=reverse_lazy('website:password_reset_complete'), template_name='website/reset/passwordResetConfirm.html'), name='password_reset_confirm'),
    path('password/reset/complete/', auth_views.PasswordResetCompleteView.as_view(template_name='website/reset/passwordResetComplete.html'), name='password_reset_complete'),
]
