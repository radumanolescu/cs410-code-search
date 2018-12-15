import metapy

class CodeRanker(metapy.index.RankingFunction):

    def __init__(self, default_ranker):
        self.default_ranker = default_ranker
        self.weights = { # weight = number of times the words should be repeated in the query, in
            # Java
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue",
            "default", "double", "do", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto",
            "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package",
            "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while", "double"
        }

        super(CodeRanker, self).__init__()

    def score(self, idx, query, top_k):

        line = query.content()
        weight = 2

        for word in self.weights.items():
            if word in line:
                line += (' ' + word) * weight
        query.content(line)

        return self.default_ranker.score(idx, query, top_k)