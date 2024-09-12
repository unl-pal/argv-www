# PAClab - The Program Analaysis ColLABoratory

## This repository contains the frontend website for PAClab.  For the transformer, see: [unl-pal/paclab-transformer](https://github.com/unl-pal/paclab-transformer)

PAClab front-end UI repository using the Django web framework. This repository also contains several back-end scripts.

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

## Requirements

- Python 3.5+
- pip

See [requirements.txt](requirements.txt) for full list of Python requirements. Note development and production environments have different requirements files (see the requirements directory). By default, production is used.

## Setup

### Base Installation
1. Install `python3`
2. Install `pip3`
3. Open terminal in location you want to install the website
4. Clone repository from GitHub (this creates folder named `paclab-www`)
5. Navigate terminal to root of project directory (where `manage.py` is): `cd paclab-www`
6. Use command `pip3 install -r requirements.txt` to install all default requirements
7. Generate a secret key and copy to the clipboard: `python manage.py shell -c "from django.core.management import utils; print(utils.get_random_secret_key())"`
8. Copy `env.template` to a file named `.env` and edit for your local configuration
9. Create migration files for database: `python3 manage.py makemigrations website backend`
10. Create database tables: `python3 manage.py migrate`

### Boa Backend Installation
1. Open terminal to where `manage.py` is located
2. Run command: `manage.py install boa` to install the Boa backend.  In the future, `boa` can be replaced with the name of other backends.
3. Run command: `manage.py enable boa` to enable Boa's filters on the web server
4. When creating a new project selector in the UI, you should see the installed filters as options in the dropdown menus.

### PACLab Backend Installation
1. Open terminal to where `manage.py` is located
2. Run command: `manage.py install paclab` to install the PAClab backend.  In the future, `paclab` can be replaced with the name of other backends.
3. When creating a new project transformer, you should see the PAClab transformer as an option.

### Backend Scripts

The backend has several scripts that need to run.  Be sure each of them is running for proper functioning.

#### Discovery Usage
1. Open terminal in the location of `manage.py`
2. Run command: `python3 manage.py rundiscovery`

#### Cloner Usage
1. Open terminal in the location of `manage.py`
2. Run command: `python3 manage.py runcloner`

#### Filters Usage
1. Open terminal in the location of `manage.py`
2. Run command: `python3 manage.py runfilters`

#### Transforms Usage
1. Open terminal in the location of `manage.py`
2. Run command: `python3 manage.py runtransforms`

### Running the Website
1. Create an admin user: `python3 manage.py createsuperuser`
2. Create static files: `python3 manage.py collectstatic`
3. Load system data: `python3 manage.py loaddata --app website basedata`
4. Run django's development server using command: `python3 manage.py runserver`

## Notes

### About Boa Backend
Currently, there is support for the Boa backend.  In the future we hope to also include support for other backends.  Backends are used to run queries and return lists of matched projects to be cloned.  Backends consist of a poller class which inherits from the poller found in the backend app in the PAClab repository.  Pollers trigger every several seconds, and query the database for any new project selectors that have yet to be processed.  Backends contain a list of filters that can be enabled on the web server when creating project selectors.

### About PAClab Backend
Currently, we have automatic support for a few transformers.  Right now, the transformations to apply to each project are created automatically, however in the future we would like users to be able to specify different transformations to for different project selections.  The transformations are installed just like filters for selectors, using various backends that users deploying PAClab will be able to swap in and out eventually.

### About Discovery Daemon
The poller runs every few seconds and inherits from a base class in the PAClab repository.  The poller checks the database for unprocessed project selectors, then generates a query based on the filters listed under the selector.  The query is then sent to the appropriate backend, which returns a list of discovered projects.

### About Filtering Daemon
The poller runs every few seconds and inherits from a base class in the PAClab repository.  The poller checks the database for unprocessed project selectors, then generates a query based on the filters listed under the selector.  The query is then sent to the appropriate backend, which returns a list of discovered projects.

### About Cloner Daemon
The cloner runs every few seconds and looks for new urls that were discovered but not yet cloned. It then uses the input dataset from the original selector to search for each project and clones them.  After completion of its activities, it sets the url's host and path name so other services can find the location in the cluster of the cloned project.

### About Transforms Daemon
The transformer runs every few seconds and processes downloaded projects.  It uses the set host and path name to find each project directory, then transforms the project source code using the default installed transformations.  It then saves an entry in the database for this fully processed project which is used for zipping the transformed project and downloading it.

### Other Notes

#### Using Apache

If you're using Ubuntu + Apache we recommend using this guide: https://www.digitalocean.com/community/tutorials/how-to-serve-django-applications-with-apache-and-mod_wsgi-on-ubuntu-16-04 to help set up Django on your server

#### Storage Layout

````
/data
    /repos
        ...
    /transformed
        /selector/pk
            /TransformOption.pk
                ...
            /TransformOption.pk
                ...
        /transform/pk
            /TransformOption.pk
                ...
            /TransformOption.pk
                ...
````
