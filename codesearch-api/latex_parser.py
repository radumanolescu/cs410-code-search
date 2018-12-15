import sys
import re
import hashlib


def latex_to_csx(latex_formula):
    words = [
        '[{}]'.format(hashlib.md5(w).hexdigest()[:5]) for w in
        re.split(r'\s|\\', latex_formula)
        if w
    ]
    return ' '.join(words)


if __name__ == '__main__':
    for line in sys.stdin.readlines():
        print(latex_to_csx(line))