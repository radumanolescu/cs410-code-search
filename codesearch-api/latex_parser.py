import sys
import re
import hashlib


def transform_digest(digest):
    return ''.join([
        ch if not str.isdigit(ch) else chr(ord(ch) + ord('K') - ord('0'))
        for ch in digest
    ])


def latex_to_csx(latex_formula):
    words = [
        'CSX{}'.format(transform_digest(hashlib.md5(w.encode('utf-8')).hexdigest()[:5].upper())) for w in
        re.split(r'\s|\\', latex_formula)
        if w
    ]
    return ' '.join(words)


if __name__ == '__main__':
    for line in sys.stdin.readlines():
        print(latex_to_csx(line.strip()))