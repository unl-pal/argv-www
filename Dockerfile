FROM python:3.11
VOLUME /app /data/repos /data/projects /transformer
WORKDIR /app
RUN pip install --upgrade pip
COPY requirements/common.txt requirements/common.txt
COPY requirements/prod.txt requirements/prod.txt
COPY requirements/dev.txt requirements/dev.txt 
RUN pip install --no-cache-dir -r requirements/dev.txt
COPY . .
EXPOSE 8000
CMD ["/bin/bash"]
