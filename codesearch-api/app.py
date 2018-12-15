import json
import flask
import logging
from flask_cors import CORS
from searcher import Searcher
# from searcher import StubSearcher as Searcher

logging.basicConfig(level='INFO')

logger = logging.getLogger(__name__)
searcher = Searcher('config.toml')

app = flask.Flask(__name__)
CORS(app)

@app.route('/ping', methods=['GET'])
def ping():
    return flask.jsonify({
        'status': 'READY',
    })


@app.route('/search', methods=['GET'])
def search():
    args = flask.request.args
    logger.info(args)
    argLst = args.getlist('query')
    q = ' '.join(argLst)
    logger.info(q)
    query = { "query" : q, "ranker": "OkapiBM25"}
    return searcher.search(query)
