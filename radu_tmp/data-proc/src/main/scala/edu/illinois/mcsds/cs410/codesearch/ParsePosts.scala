package edu.illinois.mcsds.cs410.codesearch

import java.io.{File, FileInputStream}

import javax.xml.XMLConstants
import javax.xml.stream.XMLInputFactory
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
//import javax.xml.stream.XMLEventReader

import scala.io.Source
import scala.xml.pull._

object ParsePosts {
  System.setProperty("jdk.xml.totalEntitySizeLimit", String.valueOf(Integer.MAX_VALUE))

  // "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/math.stackexchange.com/Posts.xml" // 2372549
  // "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/stackoverflow.com/Posts.xml"
  val postsFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/math.stackexchange.com/Posts.xml"
  //    val xml = new javax.xml.stream.XMLEventReader(Source.fromFile("test.xml"))

  def main(args: Array[String]): Unit = {
    val inputFactory = XMLInputFactory.newInstance()
    //    inputFactory.setProperty(XMLConstants.FEATURE_SECURE_PROCESSING, false)
    val xsr = inputFactory.createXMLStreamReader(new FileInputStream(new File(postsFileName)))

    var numElem = 0

    while (xsr.hasNext()) {
      xsr.next()
      if (xsr.isStartElement()) {
        val numAttr = xsr.getAttributeCount
        for {
          i <- 0 until numAttr
        } {
          val localName = xsr.getAttributeLocalName(i)
          //          val namespaceURI: String = xsr.getAttributeNamespace(i)
          val value = xsr.getAttributeValue(i)
          if (localName == "Body") {
            val doc: Document = Jsoup.parse(value)
            println(value)
            println(".......... .......... .......... .......... .......... .......... .......... .......... \n")
            println(doc.text())
            println("\n---------- ---------- ---------- ---------- ---------- ---------- ---------- ---------- \n")
          }
        }
        numElem += 1
        if (numElem % 100 == 0) throw new RuntimeException
        //        if (numElem % 10000 == 0) println(numElem)
      }
    }

    println("Done: " + numElem)
  }
}
