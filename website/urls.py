from django.contrib.auth import views as auth_views
from django.urls import path
from django.urls import reverse_lazy
from django.views.generic import RedirectView, TemplateView

from . import views

app_name = 'website'

urlpatterns = [
    path('favicon.ico', RedirectView.as_view(url='/static/website/favicon.ico')),

    path('', TemplateView.as_view(template_name='website/index.html'), name='index'),
    path('funding/', TemplateView.as_view(template_name='website/funding.html'), name='funding'),
    path('papers/', views.PapersView.as_view(), name='papers'),
    path('people/', views.PeopleView.as_view(), name='people'),
    path('policies/privacy/', TemplateView.as_view(template_name='website/policies/privacy.html'), name='privacy_policy'),
    path('policies/terms_of_use/', TemplateView.as_view(template_name='website/policies/terms.html'), name='tos'),

    path('login/', views.LoginView.as_view(), name='login'),
    path('logout/', views.logoutView, name='logout'),
    path('register/', views.RegisterView.as_view(), name='register'),
    path('editprofile/', views.profile, name='edit_profile'),

    path('password/change/', views.password_change, name='password_change'),
    path('password/reset/', auth_views.PasswordResetView.as_view(email_template_name='website/reset/password_reset_email.html', success_url=reverse_lazy('website:password_reset_done'), template_name='website/reset/passwordReset.html'), name='password_reset'),
    path('password/reset/done/', auth_views.PasswordResetDoneView.as_view(template_name='website/reset/passwordResetDone.html'), name='password_reset_done'),
    path('password/reset/confirm/<uidb64>/<token>/', auth_views.PasswordResetConfirmView.as_view(success_url=reverse_lazy('website:password_reset_complete'), template_name='website/reset/passwordResetConfirm.html'), name='password_reset_confirm'),
    path('password/reset/complete/', auth_views.PasswordResetCompleteView.as_view(template_name='website/reset/passwordResetComplete.html'), name='password_reset_complete'),
    path('verify/email/', views.verify_email_link, name='verify_email_link'),
    path('verify/<uidb64>/<token>/', views.verify_email, name='verify_email'),

    path('api/filter_detail/', views.api_filter_detail, name='api_filter_detail'),
    path('api/transform_param_detail/', views.api_transform_param, name='api_transform_param'),
    path('api/usernames/', views.api_usernames, name='api_usernames'),
    path('api/selections/<slug>/rename', views.api_rename_selection, name='api_rename_selection'),
    path('api/transforms/<slug>/rename', views.api_rename_transform, name='api_rename_transform'),

    path('selection/list/', views.SelectionListView.as_view(), name='list_selections'),
    path('selection/create/', views.create_selection, name='create_selection'),
    path('selection/create/manual/', views.create_manual_selection, name='create_manual_selection'),
    path('selection/<slug>/', views.selection_detail, name='selection_detail'),
    path('selection/<slug>/delete/', views.delete_selection, name='delete_selection'),
    path('selection/<slug>/download/', views.download_selection, name='download_selection'),
    path('selection/<slug>/duplicate/', views.selection_duplicate, name='selection_duplicate'),
    path('selection/<slug>/inspect/', views.SelectionInspectView.as_view(), name='selection_inspect'),
    path('selection/<slug>/inspect/cloned/', views.ClonedInspectView.as_view(), name='selection_inspect_cloned'),
    path('selection/<slug>/inspect/retained/', views.RetainedInspectView.as_view(), name='selection_inspect_retained'),
    path('selection/<slug>/inspect/download/', views.export_selection_csv, name='export_selection_csv'),
    path('selection/<slug>/inspect/cloned/download/', views.export_cloned_csv, name='export_cloned_csv'),
    path('selection/<slug>/inspect/retained/download/', views.export_retained_csv, name='export_retained_csv'),
    path('selection/<slug>/stop', views.selection_stop, name='selection_stop'),

    path('transform/list/', views.TransformListView.as_view(), name='list_transforms'),
    path('transform/create/from_selection/<slug>/', views.create_transform_selection, name='create_transform_selection'),
    path('transform/create/from_transform/<slug>/', views.create_transform_transform, name='create_transform_transform'),
    path('transform/<slug>/', views.transform_detail, name='transform_detail'),
    path('transform/<slug>/delete/', views.delete_transform, name='delete_transform'),
    path('transform/<slug>/download/', views.download_transform, name='download_transform'),
    path('transform/<slug>/duplicate/', views.transform_duplicate, name='transform_duplicate'),
    path('transform/<slug>/inspect/', views.TransformInspectView.as_view(), name='transform_inspect'),
    path('transform/<slug>/inspect/transformed/', views.TransformInspectTransformedView.as_view(), name='transform_inspect_transformed'),
    path('transform/<slug>/inspect/retained/', views.TransformInspectRetainedView.as_view(), name='transform_inspect_retained'),
    path('transform/<slug>/inspect/download/', views.export_transform_csv, name='export_transform_csv'),
    path('transform/<slug>/inspect/transformed/download/', views.export_transform_transformed_csv, name='export_transform_transformed_csv'),
    path('transform/<slug>/inspect/retained/download/', views.export_transform_retained_csv, name='export_transform_retained_csv'),
    path('transform/<slug>/stop', views.transform_stop, name='transform_stop'),
]
