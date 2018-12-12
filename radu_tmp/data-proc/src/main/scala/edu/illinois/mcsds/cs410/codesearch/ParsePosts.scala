package edu.illinois.mcsds.cs410.codesearch

import java.io.{File, FileInputStream, PrintWriter}

import javax.xml.stream.XMLInputFactory
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

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
              pw.write(s + "\n")
              numElem += 1
            }
          }
        }
      }
    }
  }
}
