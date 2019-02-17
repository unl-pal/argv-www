# PAClab - The Program Analaysis ColLABoratory

PAClab development repository using Django framework.

## This repository contains the frontend website for PAClab.

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
8. Clone repository from GitHub (this creates folder named `paclab-www`)
9. Navigate terminal to root of project directory (where `manage.py` is): `cd paclab-www`
10. Generate a secret key and copy to the clipboard: `python manage.py shell -c "from django.core.management import utils; print(utils.get_random_secret_key())"`
11. Create a file named `.env` and add these lines:
   - `SECRET_KEY='your-secret-key-from-previous-step'`
   - `DEBUG=True`
   - `ALLOWED_HOSTS='your-server-name'` or `'localhost,127.0.0.1'` if using django's packaged development server
   - `DATABASE_URL='sqlite:////db.sqlite3'` or url to your database
   - `EMAIL_USER='gmail-smtp-email-username'`
   - `EMAIL_PASSWORD='gmail-smtp-email-password'`
12. Create migration files for database: `python3 manage.py makemigrations website`
13. Create database tables: `python3 manage.py migrate`
14. Create an admin user: `python3 manage.py createsuperuser`
15. Create static files: `python3 manage.py collectstatic`
16. Run django's development server using command: `python3 manage.py runserver`

If you're using Ubuntu + Apache we recommend using this guide: https://www.digitalocean.com/community/tutorials/how-to-serve-django-applications-with-apache-and-mod_wsgi-on-ubuntu-16-04 to help set up Django on your server
