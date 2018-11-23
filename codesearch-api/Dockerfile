FROM python:3.6-alpine
RUN pip install pipenv
ADD . /app
WORKDIR /app
RUN pipenv install
CMD pipenv run flask -p 8081
