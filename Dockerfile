FROM python:3.11
VOLUME /app /data/repos /data/projects /transformer
WORKDIR /temp
RUN pip install --upgrade pip
COPY requirements/common.txt requirements/dev.txt requirements/
RUN pip install --no-cache-dir -r requirements/dev.txt
EXPOSE 8000
CMD ["/bin/bash"]
