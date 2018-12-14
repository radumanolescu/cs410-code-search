package edu.illinois.mcsds.cs410.codesearch

import java.io.{File, FileInputStream, PrintWriter}
import java.util.regex.Pattern
import javax.xml.stream.{XMLInputFactory, XMLStreamReader}
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.collection.mutable

object FindFormulas {
  System.setProperty("jdk.xml.totalEntitySizeLimit", String.valueOf(Integer.MAX_VALUE))

  val mathFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/math.stackexchange.com/Posts.xml" // 2372549
  val oflwFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/stackoverflow.com/Posts.xml"
  val dataFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/stackexchange.dat"
  val MaxElem = 1000

  def main(args: Array[String]): Unit = {
    Seq(mathFileName, oflwFileName).map(new File(_)).foreach { xmlPosts =>
      iterate(xmlPosts)
    }
  }

  def iterate(xmlPosts: File): Unit = {
    val inputFactory = XMLInputFactory.newInstance()
    val xsr = inputFactory.createXMLStreamReader(new FileInputStream(xmlPosts))
    var numElem = 0

    while (xsr.hasNext() && numElem < MaxElem) {
      xsr.next()
      processRecord(xsr)
      numElem += 1
    }
  }

  def processRecord(xsr: XMLStreamReader): Unit = {
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
            .replaceAll(Pattern.quote("$."), "\\$ .")
            .replaceAll(Pattern.quote("$,"), "\\$ ,")
            .replaceAll(Pattern.quote("$;"), "\\$ ;")
            .replaceAll(Pattern.quote("$)"), "\\$ )")
            .replaceAll(Pattern.quote("$?"), "\\$ ?")
            .trim
          if (s.indexOf('$') >= 0) {
//            println(s)
            val words = s.split(" ")
            var inFormula = false
            val formula = new mutable.ArrayBuffer[String]
            words.foreach { word =>
              if (word.startsWith("$")) inFormula = true
              if (inFormula) formula += word
              if (word.endsWith("$")) {
                inFormula = false
                if (formula.length > 0) {
                  println(formula.mkString(" "))
                  formula.clear
                }
              }
            }
          }
        }
      }
    }
  }

}
