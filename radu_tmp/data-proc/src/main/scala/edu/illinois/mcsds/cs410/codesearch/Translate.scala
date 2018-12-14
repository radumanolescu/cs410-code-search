package edu.illinois.mcsds.cs410.codesearch

object Translate {
  def main(args: Array[String]): Unit = {
    var d1 = 'A'
    var d2 = 'A'
    for {
      c <- ' ' to '~'
    } {
      if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
        println("" + c + "\tCSX" + d1 + d2)
        var d1_d2 = inc(d1, d2)
        d1 = d1_d2._1
        d2 = d1_d2._2
      }
    }
  }

  def inc(a: Char, b: Char): (Char, Char) = {
    if (b == 'Z') ((a + 1).toChar, 'A') else (a, (b + 1).toChar)
  }

}
