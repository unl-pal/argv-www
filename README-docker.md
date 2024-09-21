# Docker Development Environment

## To Build the Docker Environment

In the repository root, run the following command to build a Docker
image.

```sh
docker build -t argv:latest .
```

## To Run the Docker Environment

The built Docker image may then be run by using the following command:

```sh
docker run \
    -v ${PWD}:/app \  # Path to the application source code
    -v /path/to/transformer/:/transformer \ # Path to the transformer repository
    -v /path/to/data/repos:/data/repos \ # Path to the cloned repos directory
    -v /path/to/data/transformed:/data/transformed \ # Path to transformed repos directory
    -p 8000:8000 \
    -i -t argv:latest
```

The image will create a shell session for you to work from.
Instructions from [the readme](README.md) should be followed subject
to the notes below.

## Notes

 - Be sure to specify binding all IPs when running the development server, e.g.
    `python manage.py runserver 0.0.0.0:8000`.
 - It is recommended to set the value of `DATABASE_URL` to somewhere
   in one of the three volumes (`/app`, `/transformer`, or `/data`).
 - Similarly, it is recommended to set the location of repositories
   and transformed projects to be in the `/data` volume.
 - It may be necessary to set the `ERROR_LOG` variable manually in
   `.env`
