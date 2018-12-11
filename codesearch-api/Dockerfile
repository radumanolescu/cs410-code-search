FROM python:3.6
RUN pip install pipenv
ADD . /app
WORKDIR /app
RUN pipenv install
CMD pipenv run gunicorn -b 0.0.0.0:8081 --timeout 65535 app:app
