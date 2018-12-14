# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 23:21:49 2018

@author: Radu
"""

from searcher import Searcher

if __name__ == '__main__':
    searcher = Searcher('config.toml')
    query = { "query" : "lorem ipsum", "ranker": "OkapiBM25"}
    result = searcher.search(query)
    print(result)
