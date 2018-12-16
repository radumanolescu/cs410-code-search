import metapy

class CodeRanker(metapy.index.RankingFunction):

    def __init__(self, default_ranker):
        self.default_ranker = default_ranker
        all_keywords = [
            # Java Reserved words
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue",
            "default", "double", "do", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto",
            "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package",
            "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while", "double",
            # java.io objects
            "Closeable", "DataInput", "DataOutput", "Externalizable", "FileFilter", "FilenameFilter", "Flushable",
            "ObjectInput", "ObjectInputValidation", "ObjectOutput", "ObjectStreamConstants", "Serializable",
            "BufferedInputStream", "BufferedOutputStream", "BufferedReader", "BufferedWriter", "ByteArrayInputStream",
            "ByteArrayOutputStream", "CharArrayReader", "CharArrayWriter", "Console", "DataInputStream",
            "DataOutputStream", "File", "FileDescriptor", "FileInputStream", "FileOutputStream", "FilePermission",
            "FileReader", "FileWriter", "FilterInputStream", "FilterOutputStream", "FilterReader", "FilterWriter",
            "InputStream", "InputStreamReader", "LineNumberInputStream", "LineNumberReader", "ObjectInputStream",
            "ObjectInputStream.GetField", "ObjectOutputStream", "ObjectOutputStream.PutField", "ObjectStreamClass",
            "ObjectStreamField", "OutputStream", "OutputStreamWriter", "PipedInputStream", "PipedOutputStream",
            "PipedReader", "PipedWriter", "PrintStream", "PrintWriter", "PushbackInputStream", "PushbackReader",
            "RandomAccessFile", "Reader", "SequenceInputStream", "SerializablePermission", "StreamTokenizer",
            "StringBufferInputStream", "StringReader", "StringWriter", "Writer", "CharConversionException",
            "EOFException", "FileNotFoundException", "InterruptedIOException", "InvalidClassException",
            "InvalidObjectException", "IOException", "NotActiveException", "NotSerializableException",
            "ObjectStreamException", "OptionalDataException", "StreamCorruptedException", "SyncFailedException",
            "UnsupportedEncodingException", "UTFDataFormatException", "WriteAbortedException", "IOError",
            # java.lang objects
            "Appendable", "AutoCloseable", "CharSequence", "Cloneable", "Comparable", "Iterable", "Readable",
            "Runnable", "Thread.UncaughtExceptionHandler", "Boolean", "Byte", "Character", "Character.Subset",
            "Character.UnicodeBlock", "Class", "ClassLoader", "ClassValue", "Compiler", "Double", "Enum", "Float",
            "InheritableThreadLocal", "Integer", "Long", "Math", "Number", "Object", "Package", "Process",
            "ProcessBuilder", "ProcessBuilder.Redirect", "Runtime", "RuntimePermission", "SecurityManager", "Short",
            "StackTraceElement", "StrictMath", "String", "StringBuffer", "StringBuilder", "System", "Thread",
            "ThreadGroup", "ThreadLocal", "Throwable", "Void", "Character.UnicodeScript",
            "ProcessBuilder.Redirect.Type", "Thread.State", "ArithmeticException", "ArrayIndexOutOfBoundsException",
            "ArrayStoreException", "ClassCastException", "ClassNotFoundException", "CloneNotSupportedException",
            "EnumConstantNotPresentException", "Exception", "IllegalAccessException", "IllegalArgumentException",
            "IllegalMonitorStateException", "IllegalStateException", "IllegalThreadStateException",
            "IndexOutOfBoundsException", "InstantiationException", "InterruptedException", "NegativeArraySizeException",
            "NoSuchFieldException", "NoSuchMethodException", "NullPointerException", "NumberFormatException",
            "ReflectiveOperationException", "RuntimeException", "SecurityException", "StringIndexOutOfBoundsException",
            "TypeNotPresentException", "UnsupportedOperationException", "AbstractMethodError", "AssertionError",
            "BootstrapMethodError", "ClassCircularityError", "ClassFormatError", "Error", "ExceptionInInitializerError",
            "IllegalAccessError", "IncompatibleClassChangeError", "InstantiationError", "InternalError", "LinkageError",
            "NoClassDefFoundError", "NoSuchFieldError", "NoSuchMethodError", "OutOfMemoryError", "StackOverflowError",
            "ThreadDeath", "UnknownError", "UnsatisfiedLinkError", "UnsupportedClassVersionError", "VerifyError",
            "VirtualMachineError", "Deprecated", "Override", "SafeVarargs", "SuppressWarnings",
            # java.sql objects
            "Array", "Blob", "CallableStatement", "Clob", "Connection", "DatabaseMetaData", "Driver", "NClob",
            "ParameterMetaData", "PreparedStatement", "Ref", "ResultSet", "ResultSetMetaData", "RowId", "Savepoint",
            "SQLData", "SQLInput", "SQLOutput", "SQLXML", "Statement", "Struct", "Wrapper", "Date", "DriverManager",
            "DriverPropertyInfo", "SQLPermission", "Time", "Timestamp", "Types", "ClientInfoStatus",
            "PseudoColumnUsage", "RowIdLifetime", "BatchUpdateException", "DataTruncation", "SQLClientInfoException",
            "SQLDataException", "SQLException", "SQLFeatureNotSupportedException",
            "SQLIntegrityConstraintViolationException", "SQLInvalidAuthorizationSpecException",
            "SQLNonTransientConnectionException", "SQLNonTransientException", "SQLRecoverableException",
            "SQLSyntaxErrorException", "SQLTimeoutException", "SQLTransactionRollbackException",
            "SQLTransientConnectionException", "SQLTransientException", "SQLWarning", "JDBC"
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
            "union", "unsigned", "using", "virtual", "void", "volatile", "wchar_t", "while", "xor", "xor_eq",
            # Python
            "False", "class", "finally", "is", "return", "None", "continue", "for", "lambda", "try", "True", "def",
            "from", "nonlocal", "while", "and", "del", "global", "not", "with", "as", "elif", "if", "or", "yield",
            "assert", "else", "import", "pass", "break", "except", "in", "raise",
            # Ruby
            "alias", "and", "BEGIN", "begin", "break", "case", "class", "def", "defined", "do", "else", "elsif", "END",
            "end", "ensure", "false", "for", "if", "module", "next", "nil", "not", "or", "redo", "rescue", "retry",
            "return", "self", "super", "then", "true", "undef", "unles", "until", "when", "while", "yield", "__FILE__",
            "__LINE__",
            # JavaScript
            "else", "instanceof", "super", "boolean", "enum", "int", "switch", "break", "export", "interface",
            "synchronized", "byte", "extends", "let", "this", "case", "false", "long", "throw", "catch", "final",
            "native", "throws", "char", "finally", "new", "transient", "class", "float", "null", "true", "const", "for",
            "package", "try", "continue", "function", "private", "typeof", "debugger", "goto", "protected", "var",
            "default", "if", "public", "void", "delete", "implements", "return", "volatile", "do", "import", "short",
            "while", "double", "in", "static", "with", "alert", "frames", "outerHeight", "all", "frameRate",
            "outerWidth", "anchor", "function", "packages", "anchors", "getClass", "pageXOffset", "area",
            "hasOwnProperty", "pageYOffset", "Array", "hidden", "parent", "assign", "history", "parseFloat", "blur",
            "image", "parseInt", "button", "images", "password", "checkbox", "Infinity", "pkcs11", "clearInterval",
            "isFinite", "plugin", "clearTimeout", "isNaN", "prompt", "clientInformation", "isPrototypeOf",
            "propertyIsEnum", "close", "java", "prototype", "closed", "JavaArray", "radio", "confirm", "JavaClass",
            "reset", "constructor", "JavaObject", "screenX", "crypto", "JavaPackage", "screenY", "Date", "innerHeight",
            "scroll", "decodeURI", "innerWidth", "secure", "decodeURIComponent", "layer", "select", "defaultStatus",
            "layers", "self", "document", "length", "setInterval", "element", "link", "setTimeout", "elements",
            "location", "status", "embed", "Math", "String", "embeds", "mimeTypes", "submit", "encodeURI", "name",
            "taint", "encodeURIComponent", "NaN", "text", "escape", "navigate", "textarea", "eval", "navigator", "top",
            "event", "Number", "toString", "fileUpload", "Object", "undefined", "focus", "offscreenBuffering",
            "unescape", "form", "open", "untaint", "forms", "opener", "valueOf", "frame", "option", "window",
            "onbeforeunload", "ondragdrop", "onkeyup", "onmouseover", "onblur", "onerror", "onload", "onmouseup",
            "ondragdrop", "onfocus", "onmousedown", "onreset", "onclick", "onkeydown", "onmousemove", "onsubmit",
            "oncontextmenu", "onkeypress", "onmouseout", "onunload"
            # PHP
            "__halt_compiler", "abstract", "and", "array", "as", "break",
            "callable", "case", "catch", "class", "clone", "const", "continue", "declare", "default", "die", "do",
            "echo", "else", "elseif", "empty", "enddeclare", "endfor", "endforeach", "endif", "endswitch", "endwhile",
            "eval", "exit", "extends", "final", "finally", "for", "foreach", "function", "global", "goto", "if",
            "implements", "include", "include_once", "instanceof", "insteadof", "interface", "isset", "list",
            "namespace", "new", "or", "print", "private", "protected", "public", "require", "require_once", "return",
            "static", "switch", "throw", "trait", "try", "unset", "use", "var", "while", "xor", "yield"
             # PERL
            "END", "length",
            "setpgrp", "-B", "endgrent", "link", "setpriority", "-b", "endhostent", "listen", "setprotoent", "-C",
            "endnetent", "local", "setpwent", "-c", "endprotoent", "localtime", "setservent", "-d", "endpwent", "log",
            "setsockopt", "-e", "endservent", "lstat", "shift", "eof", "map", "shmctl", "eval", "mkdir", "shmget",
            "exec", "msgctl", "shmread", "exists", "msgget", "shmwrite", "exit", "msgrcv", "shutdown", "fcntl",
            "msgsnd", "sin", "fileno", "my", "sleep", "flock", "next", "socket", "fork", "not", "socketpair", "format",
            "oct", "sort", "formline", "open", "splice", "getc", "opendir", "split", "getgrent", "ord", "sprintf",
            "getgrgid", "our", "sqrt", "getgrnam", "pack", "srand", "gethostbyaddr", "pipe", "stat", "gethostbyname",
            "pop", "state", "gethostent", "pos", "study", "getlogin", "print", "substr", "getnetbyaddr", "printf",
            "symlink", "abs", "getnetbyname", "prototype", "syscall", "accept", "getnetent", "push", "sysopen", "alarm",
            "getpeername", "quotemeta", "sysread", "atan2", "getpgrp", "rand", "sysseek", "AUTOLOAD", "getppid", "read",
            "system", "BEGIN", "getpriority", "readdir", "syswrite", "bind", "getprotobyname", "readline", "tell",
            "binmode", "getprotobynumber", "readlink", "telldir", "bless", "getprotoent", "readpipe", "tie", "break",
            "getpwent", "recv", "tied", "caller", "getpwnam", "redo", "time", "chdir", "getpwuid", "ref", "times",
            "CHECK", "getservbyname", "rename", "truncate", "chmod", "getservbyport", "require", "uc", "chomp",
            "getservent", "reset", "ucfirst", "chop", "getsockname", "return", "umask", "chown", "getsockopt",
            "reverse", "undef", "chr", "glob", "rewinddir", "UNITCHECK", "chroot", "gmtime", "rindex", "unlink",
            "close", "goto", "rmdir", "unpack", "closedir", "grep", "say", "unshift", "connect", "hex", "scalar",
            "untie", "cos", "index", "seek", "use", "crypt", "INIT", "seekdir", "utime", "dbmclose", "int", "select",
            "values", "dbmopen", "ioctl", "semctl", "vec", "defined", "join", "semget", "wait", "delete", "keys",
            "semop", "waitpid", "DESTROY", "kill", "send", "wantarray", "die", "last", "setgrent", "warn", "dump", "lc",
            "sethostent", "write", "each", "lcfirst", "setnetent"
        ]

        self.keywords = self.f7(all_keywords)

        super(CodeRanker, self).__init__()

    def f7(self, seq):
        seen = set()
        seen_add = seen.add
        return [x for x in seq if not (x in seen or seen_add(x))]

    def score(self, idx, query, top_k=10):

        line = query.content()
        weight = 8

        for word in self.keywords:
            if word in line:
                line += ((' ' + word) * weight)
        query.content(line)

        return self.default_ranker.score(idx, query, top_k)