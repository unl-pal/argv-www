# PAClab - The Program Analaysis ColLABoratory

PAClab development repository using Django framework.

## This repository contains the frontend website for PACLab.

## Requirements
- Python3
- Pip
- Django==2.0.8
- dj-database-url==0.5.0
- python-decouple==3.1
- Pillow==5.2.0
- pytz==2018.5

## Installation
1. Install python
2. Install pip
3. Use command `pip3 install django` to install Django (2.0 or higher)
   4. Use command `pip3 install python-decouple` to install decouple
5. Use command `pip3 install dj-database-url` to install dj-database-url
6. Use command `pip3 install Pillow` to install Pillow
7. Open terminal in location you want to install the website
8. Initialize Django project: `django-admin startproject paclab` (this creates a folder named `paclab`)
9. Clone repository from GitHub (this creates folder named `paclab-www`)
10. Navigate terminal to root of project directory (where `manage.py` is): `cd paclab-www`
11. Open `../paclab/paclab/settings.py` and copy the string from the setting `SECRET_KEY`
12. Create a file named `.env` and add these lines:
   - `SECRET_KEY=your-secret-key-from-previous-step`
   - `DEBUG=True`
   - `ALLOWED_HOSTS=your-server-name` or 127.0.0.1 if using django's packaged development server
   - `DATABASE_URL=sqlite:////db.sqlite3` or url to your database
13. Create migration files for database: `python3 manage.py makemigrations website`
14. Create database tables: `python3 manage.py migrate`
15. Create an admin user: `python3 manage.py createsuperuser`
16. Create static files: `python3 manage.py collectstatic`
17. Run django's development server using command: `python3 manage.py runserver`

If you're using Ubuntu + Apache we recommend using this guide: https://www.digitalocean.com/community/tutorials/how-to-serve-django-applications-with-apache-and-mod_wsgi-on-ubuntu-16-04 to help set up Django on your server
