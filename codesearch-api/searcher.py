import json
import time
import metapy
import flask
from flask_cors import CORS
import logging
from weighted_ranker import CodeRanker

logging.basicConfig(level='INFO')
logger = logging.getLogger(__name__)

class Searcher:
    """
    Wraps the MeTA search engine and its rankers.
    From https://raw.githubusercontent.com/meta-toolkit/metapy-demos/master/searcher.py
    """
    def __init__(self, cfg):
        """
        Create/load a MeTA inverted index based on the provided config file and
        set the default ranking algorithm to Okapi BM25.
        """
        self.idx = metapy.index.make_inverted_index(cfg)
        self.default_ranker = metapy.index.OkapiBM25()

    def process_doc(self, doc_content):
        return ' '.join([
            t for t in doc_content.split(' ')
            if not t.startswith('CSX')
        ])

    def search(self, request):
        """
        Accept a JSON request and run the provided query with the specified
        ranker.
        """
        start = time.time()
        query = metapy.index.Document()
        query.content(request['query'])
        ranker_id = request['ranker']
        try:
            ranker = getattr(metapy.index, ranker_id)()
        except:
            print("Couldn't make '{}' ranker, using default.".format(ranker_id))
            ranker = self.default_ranker
        response = {'query': request['query'], 'results': []}
        for result in ranker.score(self.idx, query):
            response['results'].append({
                'score': float(result[1]),
                'rating': float(result[1]),
                'content': self.process_doc(self.idx.metadata(result[0]).get('content')),
                'matchedPositions': []
            })
        response['elapsed_time'] = time.time() - start
        return json.dumps(response, indent=2)

class WeightedSearcher(Searcher):
    def __init__(self, cfg):
        super(WeightedSearcher, self).__init__(cfg)
        self.default_ranker = CodeRanker(self.default_ranker)

class StubSearcher:
    def __init__(self, *args, **kwargs):
        pass

    def search(self, request):
        # logger.info("request: " + request)
        return flask.jsonify({
            "results": [
            {
                'content': "Lorem Ipsum is simply dummy text of the printing and typesetting $$a = b^2$$ industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                'matchedPositions': [
                    (15, 20),
                    (29, 33),
                ],
                'score': 0.75,
                'rating': 3.5
            },
            {
                'content': "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?",
                'matchedPositions': [
                    (17, 22),
                    (50, 55),
                ],
                'score': 0.71,
                'rating': 3
            }
        ]
    })