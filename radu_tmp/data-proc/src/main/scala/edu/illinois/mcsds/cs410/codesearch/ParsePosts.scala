package edu.illinois.mcsds.cs410.codesearch

import java.io.{File, FileInputStream, PrintWriter}
import java.util.regex.Pattern

import javax.xml.stream.XMLInputFactory
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.collection.mutable

object ParsePosts {
  System.setProperty("jdk.xml.totalEntitySizeLimit", String.valueOf(Integer.MAX_VALUE))

  val mathFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/math.stackexchange.com/Posts.xml" // 2372549
  val oflwFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/stackoverflow.com/Posts.xml"
  val dataFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/stackexchange.dat"
  val MaxElem = 10000

  def main(args: Array[String]): Unit = {
    implicit val pw = new PrintWriter(new File(dataFileName))
    Seq(mathFileName, oflwFileName).map(new File(_)).foreach { xmlPosts =>
      writePosts(xmlPosts)
    }
    pw.close()
    println("Done")
  }

  def writePosts(xmlPosts: File)(implicit pw: PrintWriter): Unit = {
    val inputFactory = XMLInputFactory.newInstance()
    val xsr = inputFactory.createXMLStreamReader(new FileInputStream(xmlPosts))

    var numElem = 0

    while (xsr.hasNext() && numElem < MaxElem) {
      xsr.next()
      if (xsr.isStartElement()) {
        val numAttr = xsr.getAttributeCount
        for {
          i <- 0 until numAttr
        } {
          val localName = xsr.getAttributeLocalName(i)
          val value = xsr.getAttributeValue(i)
          if (localName == "Body") {
            val doc: Document = Jsoup.parse(value)
            // Keep the parentheses in doc.text() else "ambiguous reference to overloaded definition"
            val s = doc.text()
              .replaceAll("\r", "")
              .replaceAll("\n", "\\n")
              .trim
            if (!s.isBlank) {
              augmentFormulas(s)
              numElem += 1
            }
          }
        }
      }
    }
  }

  def augmentFormulas(line: String)(implicit pw: PrintWriter): Unit = {
    if (line.indexOf('$') >= 0) {
      val s = line
        .replaceAll(Pattern.quote("$."), "\\$ .")
        .replaceAll(Pattern.quote("$,"), "\\$ ,")
        .replaceAll(Pattern.quote("$;"), "\\$ ;")
        .replaceAll(Pattern.quote("$)"), "\\$ )")
        .replaceAll(Pattern.quote("$?"), "\\$ ?")
        .trim
      val words = s.split(" ")
      var inFormula = false
      val augmented = mutable.ArrayBuffer.empty[String]
      val formula = mutable.ArrayBuffer.empty[String]
      words.foreach { word =>
        augmented += word
        if (word.startsWith("$")) inFormula = true
        if (inFormula) formula += word
        if (word.endsWith("$")) {
          inFormula = false
          if (formula.length > 0) {
            val fml = formula.mkString(" ")
            val terms = fml.split("\\s|\\\\").filterNot(_.isEmpty).map("CSX" + _)
            augmented ++= terms
          }
          formula.clear
        }
      }
      pw.write(augmented.mkString("", " ", "\n"))
    } else {
      pw.write(line + "\n")
    }

  }
}
