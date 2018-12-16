package edu.illinois.mcsds.cs410.codesearch

import java.io.{File, FileInputStream, PrintWriter}
import java.util.regex.Pattern

import javax.xml.stream.XMLInputFactory
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.collection.mutable
import CsxEncoder.latexToCsx

/**
  * Parse downloaded posts and create a line corpus suiteable for indexing.
  * Input: XML files containing q&a posts in the following format
  * posts / row / @Id @PostTypeId @AcceptedAnswerId @CreationDate @Score @ViewCount @Body @OwnerUserId @LastEditorUserId @LastEditorDisplayName @LastEditDate @LastActivityDate @Title @Tags @AnswerCount @CommentCount @FavoriteCount
  * Output: text file containing one post per row, i.e. in the MeTA line corpus format.
  * The posts are extracted from the @Body attribute. The HTML tags are removed but the LaTeX formulas are kept.
  * For each LaTeX formula, compute the CSX token sequence (see implementation) and add the tokens immediately following the formula.
  */
object ParsePosts {
  System.setProperty("jdk.xml.totalEntitySizeLimit", String.valueOf(Integer.MAX_VALUE))

  // Input files
  val mathFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/math.stackexchange.com/Posts.xml" // 2372549
  val oflwFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/stackoverflow.com/Posts.xml"
  // Output file, i.e. line corpus
  val dataFileName = "/Users/Radu Manolescu/-/Study/MCS-DS/CS-410/prj/data/stackexchange.dat"
  // Max number of posts to take from each file
  val MaxElem = 10000

  /**
    * Parse the input files, process the data and write the output
    */
  def main(args: Array[String]): Unit = {
    implicit val pw = new PrintWriter(new File(dataFileName))
    Seq(mathFileName, oflwFileName).map(new File(_)).foreach { xmlPosts =>
      writePosts(xmlPosts)
    }
    pw.close()
    println("Done")
  }

  /**
    * For one input file, process the data and write to the output
    *
    * @param xmlPosts file containing q&a posts in XML format
    * @param pw       print write for the output file
    */
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
            if (!s.isEmpty) {
              augmentFormulas(s)
              numElem += 1
            }
          }
        }
      }
    }
  }

  /**
    * Process one line of text and write it to the output.
    * If any formulas are found, for each formula:
    * compute the corresponding CSX representation
    * add the CSX sequence after the formula
    * write the "augmented" formula (i.e. LaTeX formula + CSX sequence) to the output
    */
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
            val latex = formula.mkString(" ")
            augmented += latexToCsx(latex)
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
