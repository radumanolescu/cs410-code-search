package edu.illinois.mcsds.cs410.codesearch

object CsxHash {

  val formula = "$\\vec p_1$"

  def main(args: Array[String]): Unit = {
    println(csxHash(formula))
  }

  def csxHash(s: String): String = {
    var sum = 0L
    s.toCharArray.foreach { c =>
      var n = 1
      while (n < 100000) n *= c
      sum += n
    }
    val t = sum.toString
    val l = t.length
    val u = t.substring(l - 5).toCharArray.map(c => (c + 'K' - '0').toChar)
    "CSX" + new String(u)
  }

}
