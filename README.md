# PAClab Project

# PAClab development repository using django framework

Requirements -
Python3
Pip
dj-database-url==0.5.0
Django==2.0.8
python-decouple==3.1
Pillow==5.2.0
pytz==2018.5

1. Install python
2. Install pip
3. Use pip to install django 2.0 or higher
4. Use command: pip install python-decouple
   to install decouple
5. Use command: pip install dj-database-url
   to install dj-database-url
6. Open terminal in location you want to install package
7. Use command: django-admin startproject paclab
8. Open paclab/settings.py and cut the string from the setting SECRET_KEY, save this for a few steps later
9. Copy repository from GitHub link into your project directory
10. Create a file named .env and add these lines:
    SECRET_KEY=your-secret-key-from-earlier
    DEBUG=True
    ALLOWED_HOSTS=your-server-name or localhost if using django's packaged development server
    DATABASE_URL=sqlite:///db.sqlite3 or url to your database
11. Navigate terminal to root of project directory (where manage.py is)
12. Use command: python3 manage.py createsuperuser
    and follow prompts to create an admin user
13. Use command: python3 makemigrations website
    to create migration files for database
14. Use command: python3 migrate
    to create your tables in your database
15. Run django's development server using command: python3 manage.py runserver

If you're using Ubuntu + apache we recommend using this guide: https://www.digitalocean.com/community/tutorials/how-to-serve-django-applications-with-apache-and-mod_wsgi-on-ubuntu-16-04 to help set up django on your server