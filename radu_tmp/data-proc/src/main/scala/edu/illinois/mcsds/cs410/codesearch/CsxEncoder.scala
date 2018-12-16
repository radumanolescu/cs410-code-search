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
    implicit val pw = new PrintWriter(new File(csxjFileName))
    Seq(formFileName).map(new File(_)).foreach { file =>
      val source = Source.fromFile(file)
      source.getLines().foreach { latex =>
        writeFormula(latex)
      }
    }
    pw.close()
    println("Done")
  }

  def writeFormula(latex: String)(implicit pw: PrintWriter): Unit = {
    pw.write(latexToCsx(latex) + "\n")
  }

  def latexToCsx(latex: String): String = {
    if (latex.isEmpty) latex else {
      var endOfDollarPrefix = 2
      while (endOfDollarPrefix >= 0 && latex.charAt(endOfDollarPrefix) != '$') endOfDollarPrefix -= 1

      var startOfDollarSuffix = latex.length - 2
      while (startOfDollarSuffix < latex.length && latex.charAt(startOfDollarSuffix) != '$') startOfDollarSuffix += 1
      val formula = latex.substring(endOfDollarPrefix + 1, startOfDollarSuffix)
      val words = formula.split("\\s|\\\\").filterNot(_.isEmpty)
      val mdWords = words.map(md5)
      val csx = mdWords.mkString(" ")
//      println(s"[$latex] -> [$csx]")
      csx
    }
  }

  def md5(s: String): String = {
    md.update(s.getBytes)
    val digest = md.digest
    val no1 = new BigInteger(1, digest)

    var hd = no1.toString(16)
    while ( hd.length() < 32 ) { hd = "0" + hd; } // pad with leading 0's
    val letters = hd.toCharArray
          .map(c => if (Character.isDigit(c)) (c + 'K' - '0').toChar else c)
    "CSX" + new String(letters).toUpperCase.substring(0, 5)
  }

}
