import metapy

class CodeRanker(metapy.index.RankingFunction):

    def __init__(self, default_ranker):
        self.default_ranker = default_ranker
        self.weights = { # weight = number of times the words should be repeated in the query, in
            # Java
            "double":  2,
            "do": 2,
            "else": 2,
            "for": 2,
            "if": 2,
            "this": 2,
            "while": 2,
            # C -- no unique reserved words
            # C++
            "and": 1,
            "or": 1,
            "not": 2,
            # Python
            "is": 2,
            "from": 2,
            "with": 2,
            "as": 2,
            "except": 2,
            "in": 2,
            # Ruby
            "next": 2,
            "then": 2,
            "unless": 2,
            "until": 2,
            "when": 2
        }

        super(CodeRanker, self).__init__()

    def score(self, idx, query, top_k):

        line = query.content()

        for word, weight in self.synonyms.items():
            if word in line:
                line += (' ' + word) * weight
        query.content(line)

        return self.default_ranker.score(idx, query, top_k)