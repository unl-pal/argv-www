# Docker Development Environment

## To Build the Docker Environment

In the repository root, run the following command to build a Docker
image.

```sh
docker build  \
    -v ${PWD}:/app \  # Path to the application source code
    -v /path/to/transformer/:/transformer \ # Path to the transformer repository
    -v /path/to/data/data:/data \ # Path to the 
    -t argvsite .
```

## To Run the Docker Environment

The built Docker image may then be run by using the following command:

```sh
docker run \
    -v ${PWD}:/app \
    -v /path/to/transformer/:/transformer \
    -v /path/to/data:/data \
    -p 8000:8000 \
    -i -t argvsite:latest
```

The image will create a Bash shell session for you to work from.
Instructions from [the readme](README.md) should be followed subject
to the notes below.

## Notes

 - It is recommended to set the value of `DATABASE_URL` to  somewhere in one of the three volumes (`/app`, `/transformer`, or `/data`).
 - Similarly, it is recommended to set the location of repositories and transformed projects to be in the `/data` volume.
 - It may be necessary to set the `ERROR_LOG` variable manually.
