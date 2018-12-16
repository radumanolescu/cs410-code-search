```
  ____          _        ____                      _
 / ___|___   __| | ___  / ___|  ___  __ _ _ __ ___| |__
| |   / _ \ / _` |/ _ \ \___ \ / _ \/ _` | '__/ __| '_ \
| |__| (_) | (_| |  __/  ___) |  __/ (_| | | | (__| | | |
 \____\___/ \__,_|\___| |____/ \___|\__,_|_|  \___|_| |_|

```

# cs410-code-search

Demo site: [http://cs410.idempotent.io](http://cs410.idempotent.io)

Shared project repo for CodeSearch team, CS410-2018

Team members:

* Kevin Jing Qiu (kevinjqiu, jingq2@illinois.edu)  - coordinator
* David Landrith (davidlandrith, dkl2@illinois.edu)
* Radu Manolescu (radumanolescu, radufm2@illinois.edu)

# How to run with docker

* First you need to install [docker](https://docker.io) for your distribution.
* Next, install [docker-compose](https://docs.docker.com/compose/install/).  If you have python/pip, it's as simple as `pip install docker-compose`.
* `cd` into the directory where `docker-compose.yml` is, and then:


    docker-compose up -d

You can check the logs:

    docker-compose logs

After the containers are up, the API server will be available at `http://localhost:8081` and the UI will be available at `http://localhost:8080`.  You can change those values in the `docker-compose.yml` file.

This docker-compose file is using volume mounting so the code change in the local folder will be reflected in the container, however, you need to restart the containers in order to make those changes take effect:

    docker-compose restart

Contributions
=============

#### Kevin Qiu (jingq2@illinois.edu)

* Project setup
    * Project bootstrapping (frontend+backend)
    * dockerfile, docker-compose and deployment automation
* Frontend
    * UI/UX design/implementation
    * LaTex formula button / Document rendering with formula (integration with MathJAX)
* Backend
    * Search API schema
    * Implemented LaTex to CSX translation
    * Implemented parsing of raw search query to search terms
