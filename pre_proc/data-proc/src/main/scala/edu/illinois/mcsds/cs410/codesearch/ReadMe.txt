Formulas in the Math Exchange site are written in LaTex, e.g.
$\sqrt{2}$
$$u\cdot v = 0 \qquad \Longleftrightarrow \qquad \| u \|^2 + \|v\|^2 = \|u-v\|^2 \ .$$

Our goal is to encode these formulas into pseudo-words that will be indexed (and not filtered out) by MeTA.

For this, the following need to happen:
    encode the punctuation marks and numbers as alpha characters
    obtain "words" longer than three chars
    prepend a unique prefix (arbitrarily chosen: "CSX")

On the back end:
    loop over the Math posts and extract the text of the post
        e.g. "The square root of 2 is $\sqrt{2}$"
    find the LaTex fragment in the post and encode it
        e.g. "$p^2$" becomes "CSXMSUP CSXMI  CSXp CSXMN  CSX2"
    add the CSX sequence to the text of the post
        e.g. "The square root of 2 is $\sqrt{2}$ CSXMSUP CSXMI  CSXp CSXMN  CSX2"
    write the "enhanced" post to a text file, which becomes a MeTA line corpus
    have MeTA index the corpus with full text option, i.e. store the text with the index
        the formulas will be discarded, but the CSX tokens will be indexed, because they look like words
        the CSX tokens are sufficiently correlated to the LaTex query that finding the token representation of the formula is the same as finding the LaTex formula

On the front end
    add a graphical LaTex editor, e.g. https://www.mathjax.org/#demo
    users can now compose a query graphically, without knowing LaTex
    the LaTex query is converted to CSX tokens the same way as on the back end
    the user query (normal words + CSX tokens) is sent to the back end

On the back end
    search the index for the user query and get results
    remove from the results all words prefixed with CSX
    send results to front end
