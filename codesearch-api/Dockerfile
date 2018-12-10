FROM python:3.6
RUN pip install pipenv
ADD . /app
WORKDIR /app
RUN pipenv install
CMD pipenv run flask run -p 8081 -h 0.0.0.0
