import sys
import json

from searcher import Searcher

if __name__ == '__main__':
    if len(sys.argv) != 2:
        print("Usage: {} config.toml".format(sys.argv[0]))
        sys.exit(1)
    config = sys.argv[1]
    searcher = Searcher(config)
    query = json.loads('{ "query" : "incompressible", "ranker": "OkapiBM25"}')
    result = searcher.search(query)
    print(result)
