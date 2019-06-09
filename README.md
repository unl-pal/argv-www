# PAClab - The Program Analaysis ColLABoratory

PAClab development repository using Django framework.

## This repository contains the frontend website for PAClab.

## Requirements
See [requirements.txt](requirements.txt) for official list.
Note that development and production environments have different requirements files. By default, production will be used.

## Installation
1. Install `python3`
2. Install `pip3`
3. Use command `pip3 install -r requirements.txt` to install all default requirements
4. Open terminal in location you want to install the website
5. Clone repository from GitHub (this creates folder named `paclab-www`)
6. Navigate terminal to root of project directory (where `manage.py` is): `cd paclab-www`
7. Generate a secret key and copy to the clipboard: `python manage.py shell -c "from django.core.management import utils; print(utils.get_random_secret_key())"`
8. Create a file named `.env` and add these lines:
   - `SECRET_KEY='your-secret-key-from-previous-step'`
   - `DEBUG=True`
   - `ALLOWED_HOSTS='your-server-name'` or `'localhost,127.0.0.1'` if using django's packaged development server
   - `DATABASE_URL='sqlite:////full/path/to/paclab-www/db.sqlite3'` or url to your database
   - `EMAIL_USER='gmail-smtp-email-username'`
   - `EMAIL_PASSWORD='gmail-smtp-email-password'`
9. Create migration files for database: `python3 manage.py makemigrations website backend`
10. Create database tables: `python3 manage.py migrate`
11. Create an admin user: `python3 manage.py createsuperuser`
12. Create static files: `python3 manage.py collectstatic`
13. Run django's development server using command: `python3 manage.py runserver`

If you're using Ubuntu + Apache we recommend using this guide: https://www.digitalocean.com/community/tutorials/how-to-serve-django-applications-with-apache-and-mod_wsgi-on-ubuntu-16-04 to help set up Django on your server
