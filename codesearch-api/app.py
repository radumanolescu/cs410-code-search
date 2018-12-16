import json
import flask
import logging
import re
from latex_parser import latex_to_csx
from flask_cors import CORS
from searcher import Searcher
from searcher import WeightedSearcher

logging.basicConfig(level='INFO')

logger = logging.getLogger(__name__)

app = flask.Flask(__name__)
CORS(app)

@app.route('/ping', methods=['GET'])
def ping():
    return flask.jsonify({
        'status': 'READY',
    })


def parse_query(query):
    """Parse the query string, convert to CSX tokens if the query term is latex"""
    remaining = str(query)
    modified_query = ""
    while len(remaining) > 0:
        m = re.search(r'\${1,2}(.+?)\${1,2}', remaining)
        if m:
            modified_query += remaining[:m.start()]
            csx_tokens = latex_to_csx(m.group(1))
            modified_query += csx_tokens
            remaining = remaining[m.end()+1:]
        else:
            modified_query += " " + remaining
            remaining = ""
    return modified_query


@app.route('/search', methods=['GET'])
def search():
    searcher = Searcher('config.toml')
    args = flask.request.args
    logger.info(args)
    argLst = args.getlist('query')
    q = ' '.join(argLst)
    q = parse_query(q)
    logger.info(f'Parsed query: {q}')
    query = {"query" : q, "ranker": "OkapiBM25"}
    return searcher.search(query)

@app.route('/codesearch', methods=['GET'])
def codesearch():
    searcher = WeightedSearcher('config.toml')
    args = flask.request.args
    logger.info(args)
    argLst = args.getlist('query')
    q = ' '.join(argLst)
    logger.info(q)
    logger.info("using the weighted query")
    query = {"query" : q}
    return searcher.search(query)
