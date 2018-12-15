import metapy

class CodeRanker(metapy.index.RankingFunction):

    def __init__(self, default_ranker):
        self.default_ranker = default_ranker
        self.keywords = [ # weight = number of times the words should be repeated in the query, in
            # Java
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue",
            "default", "double", "do", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto",
            "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package",
            "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while", "double",
            # C
            "auto", "break", "case", "char", "const", "continue", "default", "do", "double", "else", "enum", "extern",
            "float", "for", "goto", "if", "inline", "int", "long", "register", "restrict", "return", "short", "signed",
            "sizeof", "static", "struct", "switch", "typedef", "union", "unsigned", "void", "volatile", "while",
            "_Alignas", "_Alignof", "_Atomic", "_Bool", "_Complex", "_Generic", "_Imaginary", "_Noreturn",
            "_Static_assert", "_Thread_local", "alignas", "alignof", "atomic_bool", "atomic_char", "atomic_schar",
            "atomic_uchar", "atomic_short", "atomic_ushort", "atomic_int", "atomic_uint", "atomic_long", "atomic_ulong",
            "atomic_llong", "atomic_ullong", "atomic_char16_t", "atomic_char32_t", "atomic_wchar_t",
            "atomic_int_least8_t", "atomic_uint_least8_t", "atomic_int_least16_t", "atomic_uint_least16_t",
            "atomic_int_least32_t", "atomic_uint_least32_t", "atomic_int_least64_t", "atomic_uint_least64_t",
            "atomic_int_fast8_t", "atomic_uint_fast8_t", "atomic_int_fast16_t", "atomic_uint_fast16_t",
            "atomic_int_fast32_t", "atomic_uint_fast32_t", "atomic_int_fast64_t", "atomic_uint_fast64_t",
            "atomic_intptr_t", "atomic_uintptr_t", "atomic_size_t", "atomic_ptrdiff_t", "atomic_intmax_t",
            "atomic_uintmax_t", "if", "elif", "else", "endif", "defined", "ifdef", "ifndef", "define", "undef",
            "include", "line", "error", "pragma",
            # C++
            "alignas", "alignof", "and", "and_eq", "asm", "atomic_cancel", "atomic_commit", "atomic_noexcept", "auto",
            "bitand", "bitor", "bool", "break", "case", "catch", "char", "char8_t", "char16_t", "char32_t", "class",
            "compl", "concept", "const", "consteval", "constexpr", "const_cast", "continue", "co_await", "co_return",
            "alignas", "alignof", "and", "and_eq", "asm", "atomic_cancel", "atomic_commit", "atomic_noexcept",
            "auto", "bitand", "bitor", "bool", "break", "case", "catch", "char", "char8_t", "char16_t", "char32_t",
            "class", "compl", "concept", "const", "consteval", "constexpr", "const_cast", "continue", "co_await",
            "co_return", "co_yield", "decltype", "default", "delete", "do", "double", "dynamic_cast", "else",
            "enum", "explicit", "export", "extern", "false", "float", "for", "friend", "goto", "if", "import", "inline",
            "int", "long", "module", "mutable", "namespace", "new", "noexcept", "not", "not_eq", "nullptr", "operator",
            "or", "or_eq", "private", "protected", "public", "reflexpr", "register", "reinterpret_cast", "requires",
            "return", "short", "signed", "sizeof", "static", "static_assert", "static_cast", "struct", "switch",
            "synchronized", "template", "this", "thread_local", "throw", "true", "try", "typedef", "typeid", "typename",
            "union", "unsigned", "using", "virtual", "void", "volatile", "wchar_t", "while", "xor", "xor_eq"
        ]

        super(CodeRanker, self).__init__()

    def score(self, idx, query, top_k):

        line = query.content()
        weight = 2

        for word in self.keywords:
            if word in line:
                line += (' ' + word) * weight
        query.content(line)

        return self.default_ranker.score(idx, query, top_k)