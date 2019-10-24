# PAClab - The Program Analaysis ColLABoratory

PAClab development repository using Django framework.

- [Requirements](#Requirements)
- [Setup](#Setup)
  * [Base Installation](#Base-Installation)
  * [Boa Backend Installation](#Boa-Backend-Installation)
  * [PACLab Backend Installation](#PACLab-Backend-Installation)
  * [Cloner Usage](#Cloner-Usage)
  * [Poller Usage](#Poller-Usage)
  * [Transformer Usage](#Transformer-Usage)
  * [Running the Website](#Running-the-Website)
- [Notes](#Notes)
  * [About Boa Backend](#About-Boa-Backend)
  * [About PAClab Backend](#About-PAClab-Backend)
  * [About Cloner Daemon](#About-Cloner-Daemon)
  * [About Poller Daemon](#About-Poller-Daemon)
  * [About Transformer Daemon](#About-Transformer-Daemon)
  * [Other Notes](#Other-Notes)

## This repository contains the frontend website for PAClab.

## Requirements
See [requirements.txt](requirements.txt) for official list.
Note that development and production environments have different requirements files. By default, production will be used.

## Setup

### Base Installation
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
   - `BOA_USER='your boa username'`
   - `BOA_PW='your boa password'`
   - `REPO_PATH='/path/to/store/repositories'`
   - `TRANSFORMED_PATH='/path/to/store/transformed/projects'`
   - `PACLAB_TRANSFORM_PATH='/path/to/paclabs/transformer'`
9. Create migration files for database: `python3 manage.py makemigrations website backend`
10. Create database tables: `python3 manage.py migrate`

### Boa Backend Installation
1. Open terminal to where manage.py is located
2. Run command: `manage.py install boa` to install the boa backend.  In the future, `boa` can be replaced with the names of other backends.
3. Run command: `manage.py enable boa` to enable boa's filters on the web server
4. When going to the new project selector section using the UI, you should see the installed filters as options in the dropdown menus.

### PACLab Backend Installation
1. Open terminal to where manage.py is located
2. Run command: `manage.py install paclab` to install the boa backend.  In the future, `paclab` can be replaced with the names of other backends.
3. When creating a new project selector, a project transformer will be created automatically and attached to the selector.

### Cloner Usage
1. Open terminal in the location of manage.py
2. Run command: `manage.py runcloner`

### Poller Usage
1. Open terminal in the location of manage.py
2. Run command: `manage.py runpoller`

### Transformer Usage
1. Open terminal in the location of manage.py
2. Run command: `manage.py runtransformer`

### Running the Website

1. Create an admin user: `python3 manage.py createsuperuser`
2. Create static files: `python3 manage.py collectstatic`
3. Run django's development server using command: `python3 manage.py runserver`

## Notes

### About Boa Backend
Currently, there is support for the BOA backend.  In the future we hope to also include support for other backends.  Backends are used to run queries and return lists of matched projects to be cloned.  Backends consists of a poller class which inherits from the poller found in the backend app in the PAClab repository.  Pollers trigger every several seconds, and query the database for any new project selectors that have yet to be processed.  Backends contain a list of filters that can be enabled on the web server when creating project selectors.

### About PAClab Backend
Currently, we have automatic support for a few transformers.  Right now, the transformations to apply to each project are created automatically, however in the future we would like users to be able to specify different transformations to for different project selections.  The transformations are installed just like filters for selectors, using various backends that users deploying PAClab will be able to swap in and out eventually.

### About Cloner Daemon
Structured very similarly to the poller, the cloner runs every few seconds and looks for new urls that have not been processed yet. I then uses the input dataset from the original selector to search for each project and clones them.  After completion of its activities, it sets the url's host and path name so other services can find the location of the donwloaded project.

### About Poller Daemon
The poller runs every few seconds and inherits from a base class in the PAClab repository.  The poller checks the database for unprocessed project selectors, then generates a query based on the filters listed under the selector.  The query is then sent to the appropriate backend, which returns a list of matched projects.

### About Transformer Daemon
Structured very similarly to the clone, the transformer runs every few seconds and processes downloaded projects.  It uses the set host and path name to find each project directory, then transforms the project source code using the default installed transformations.  It then saves an entry in the database for this fully processed project which is used for zipping the transformed project and downloading it.

### Other Notes

If you're using Ubuntu + Apache we recommend using this guide: https://www.digitalocean.com/community/tutorials/how-to-serve-django-applications-with-apache-and-mod_wsgi-on-ubuntu-16-04 to help set up Django on your server
