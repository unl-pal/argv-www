FROM python:3.11
VOLUME /app /data/repos /data/projects /transformer
WORKDIR /temp
COPY requirements/common.txt requirements/dev.txt requirements/
RUN pip install --upgrade pip && pip install --no-cache-dir -r requirements/dev.txt
WORKDIR /app
EXPOSE 8000
CMD ["/bin/bash"]
