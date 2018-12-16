package edu.illinois.mcsds.cs410.codesearch

import java.io.{File, PrintWriter}
import java.math.BigInteger
import java.security.MessageDigest

import scala.io.Source

object CsxEncoder {

  val formFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/cs410-code-search/codesearch-api/stackexchange/SampleFormulas.txt"
  val csxjFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/cs410-code-search/codesearch-api/stackexchange/FormulasCsxJava.txt"
  val md = MessageDigest.getInstance("MD5")

  def main(args: Array[String]): Unit = {
    testWithExamples
    println("Done")
  }

  def testWithExamples: Unit = {
    val formulas = Seq("", "$", "$$", "$a$", "$ab$", "$$$$", "$$a$$")
    formulas.foreach(f => println(f + " -> " + latexToCsx(f)))
  }

  def testWithFile: Unit = {
    implicit val pw = new PrintWriter(new File(csxjFileName))
    Seq(formFileName).map(new File(_)).foreach { file =>
      val source = Source.fromFile(file)
      source.getLines().foreach { latex =>
        writeFormula(latex)
      }
    }
    pw.close()
  }

  def writeFormula(latex: String)(implicit pw: PrintWriter): Unit = {
    pw.write(latexToCsx(latex) + "\n")
  }

  def latexToCsx(latex: String): String = {
    if (latex.isEmpty || latex.length < 3) "" else {
      var formStart = 0
      while (formStart < 3 && formStart < latex.length && latex.charAt(formStart) == '$') formStart += 1
      var formEnd = latex.length
      while (formEnd > latex.length - 3 && formEnd >= formStart && latex.charAt(formEnd - 1) == '$') formEnd -= 1

      if (formStart <= formEnd) {
        val formula = latex.substring(formStart, formEnd)
        val words = formula.split("\\s|\\\\").filterNot(_.isEmpty)
        val mdWords = words.map(md5)
        val csx = mdWords.mkString(" ")
        csx
      } else ""
    }
  }

  def md5(s: String): String = {
    md.update(s.getBytes)
    val digest = md.digest
    val no1 = new BigInteger(1, digest)

    var hd = no1.toString(16)
    while (hd.length() < 32) {
      hd = "0" + hd
    } // pad with leading 0's
    val letters = hd.toCharArray
      .map(c => if (Character.isDigit(c)) (c + 'K' - '0').toChar else c)
    "CSX" + new String(letters).toUpperCase.substring(0, 5)
  }

}
