package edu.illinois.mcsds.cs410.codesearch

import java.io.{File, FileInputStream, PrintWriter}
import java.util.regex.Pattern

import javax.xml.stream.{XMLInputFactory, XMLStreamReader}
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import uk.ac.ed.ph.snuggletex.{SnuggleEngine, SnuggleInput}

import scala.collection.mutable
import scala.util.control.NonFatal
import scala.xml.{Node, XML}

object FindFormulas {
  System.setProperty("jdk.xml.totalEntitySizeLimit", String.valueOf(Integer.MAX_VALUE))

  val mathFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/math.stackexchange.com/Posts.xml" // 2372549
  val oflwFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/stackoverflow.com/Posts.xml"
  val dataFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/formulas.txt"
  val MaxElem = 100
  val latex = mutable.Set.empty[String]
  val engine = new SnuggleEngine()
  val session = engine.createSession()
  implicit val pw = new PrintWriter(new File(dataFileName))

  def main(args: Array[String]): Unit = {
    Seq(mathFileName).map(new File(_)).foreach { xmlPosts =>
      iterate(xmlPosts)
    }
    println(latex)
    pw.close()
    println("Done")
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
            pw.println("\n----------\n" + s)
            val words = s.split(" ")
            var inFormula = false
            val formula = mutable.ArrayBuffer.empty[String]
            words.foreach { word =>
              if (word.startsWith("$")) inFormula = true
              if (inFormula) formula += word
              if (word.endsWith("$")) {
                inFormula = false
                if (formula.length > 0) {
                  val fml = formula.mkString(" ")
                  val l2x = latex2xml(fml)
                  val csx = xml2csx(l2x)
                  if (!csx.isEmpty) {
                    pw.println(fml)
                    pw.println(l2x)
                    pw.println(csx + "\n")
                  }
                  val x = l2x.replaceAll("<", " <").replaceAll(">", "> ")
                    .split(" ").filter(s => s.startsWith("<") && !s.startsWith("</"))
                  latex ++= x
                }
                formula.clear
              }
            }
          }
        }
      }
    }
  }

  def xml3csx(xml: String): String = {
    val csx = mutable.ArrayBuffer.empty[String]
    try {
      val elem = XML.loadString(xml).child
      elem.foreach { node =>
        csx += {
          "CSX" + node.label.toUpperCase + " " + {
            val t = node.text
            if (t.length > 2) t else "CSX" + t
          }
        }
      }
      csx.mkString(" ")
    } catch {
      case NonFatal(_) => ""
    }
  }

  def xml2csx(xml: String): String = {
    try {
      val elem = XML.loadString(xml).child
      elem.map(x2c).mkString(" ")
    } catch {
      case NonFatal(_) => ""
    }
  }

  def x2c(n: Node): String = {
    val c = n.child.length
    val l = n.label.toUpperCase
    val lbl = if (l == "#PCDATA") "" else "CSX" + l
    lbl + " " + {
      if (c == 0) {
        val t = n.text
        if (t.length > 2) t else "CSX" + t
      } else {
        n.child.map(x2c).mkString(" ")
      }
    }
  }

  def latex2xml(latex: String): String = {
    try {
      session.reset
      session.parseInput(new SnuggleInput(latex))
      session.buildXMLString()
    } catch {
      case NonFatal(_) =>
        ""
    }
  }


}
