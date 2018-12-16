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
#### Radu Manolescu (radufm2@illinois.edu)
* Data pre-processor
    * Design and implementation
* CSX encoding
    * Concept and original algorithm
    * Implemented LaTex to CSX translation
* Backend
    * MeTA line corpus design
    * processing of raw input to MeTA line corpus
    * Implementation of Python searcher using metapy
#### David K Landrith (dkl2@illinois.edu)
* Backend
    * Code search implementation
      * Remove programming language keywords from stop words
      * Implement ranker class to boost rankings of keywords from common programming languages in search engine (viz., Java, C, C++, Python, Ruby, PHP, Perl)
      * Configuration to suit computer language keyword searching 

Important files and directories
=============
#### ./codesearch-api
* Website back end, contains Python code to search in the MeTA index
#### ./codesearch-api/idx/inv
* MeTA inverted index. If you make changes to the corpus or the config file, remove this directory. It will be re-generated when needed.
#### ./codesearch-api/stackexchange
* Contains the corpus and files that can be used to test and debug the site
    * line.toml - File describing the corpus
    * stackexchange.dat - MeTA line format corpus
    * SampleFormulas.txt - sample LaTeX formulas known to exist in the corpus
    * FormulasCsxJava.txt, FormulasCsxPython.txt - CSX encodings of the formulas from SampleFormulas.txt, done through pre-processor and search engine. Must be identical.
#### ./codesearch-ui
* Web app front end
#### ./codesearch-ui/public
* Web app static assets, e.g. the app front (and only) page
#### ./codesearch-ui/src
* Web app logic, coded in JavaScript
#### ./codesearch-ui/src/components
* Web app logic, coded in JavaScript, using the Vue framework
#### ./pre_proc/data-proc
* Data pre-processing utility, written in Scala
    * Reads the input files
    * Extracts the posts
    * Finds the formulas and computes the CSX representation of the formulas
    * Writes the post text, formulas and CSX representation into the corpus
    * To execute, open build.sbt as a project file in IntelliJ
    * CsxEncoder.scala implements the CSX encoding for the pre-processor
    * ParsePosts.scala is the pre-processor, used to create the corpus
