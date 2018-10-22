from django.urls import path
from . import views

app_name = 'website'

urlpatterns = [
    path('', views.IndexView.as_view(), name='index'),
    path('funding', views.FundingView.as_view(), name='funding'),
    path('papers/', views.PapersView.as_view(), name='papers'),
    path('papers/<pk>/', views.PaperDetails.as_view(), name='paperDetails'),
    path('people/', views.PeopleView.as_view(), name='people'),
    path('login/', views.LoginView.as_view(), name='login'),
    path('logout/', views.logoutView, name='logout'),
    path('register/', views.RegisterView.as_view(), name='register'),
    path('editprofile/', views.EditProfile.as_view(), name='editProfile'),
]
