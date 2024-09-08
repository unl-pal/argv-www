FROM python:3.11.9-alpine3.20
VOLUME /app /data/repos /data/transformed /transformer
RUN apk add --update-cache \
    bash \
    git \
 && rm -rf /var/cache/apk/*
WORKDIR /temp
COPY requirements/common.txt requirements/dev.txt requirements/
RUN pip install --upgrade pip \
 && pip install --no-cache-dir -r requirements/dev.txt
WORKDIR /app
EXPOSE 8000
CMD ["/bin/bash"]
