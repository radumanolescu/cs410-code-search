package edu.illinois.mcsds.cs410.codesearch

import uk.ac.ed.ph.snuggletex.{SnuggleEngine, SnuggleInput}

object TrySnuggle {

  def main(args: Array[String]): Unit = {
    val engine = new SnuggleEngine()
    val session = engine.createSession()
    // """$$ u\cdot v = 0 \qquad \Longleftrightarrow \qquad \| u \|^2 + \|v\|^2 = \|u-v\|^2 \ . $$"""
    // "$$ x+2=3 $$"
    val input = new SnuggleInput("""$$ u\cdot v = 0 \qquad \Longleftrightarrow \qquad \| u \|^2 + \|v\|^2 = \|u-v\|^2 \ . $$""")
    session.parseInput(input)
    val xmlString = session.buildXMLString()
    println("Input " + input.getString + " was converted to:\n" + xmlString)
  }

}
