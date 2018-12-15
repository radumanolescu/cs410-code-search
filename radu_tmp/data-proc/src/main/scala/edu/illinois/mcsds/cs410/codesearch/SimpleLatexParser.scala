package edu.illinois.mcsds.cs410.codesearch

import java.util.Base64
import collection.JavaConverters._
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object SimpleLatexParser {

  val md = MessageDigest.getInstance("MD5")

  def main(args: Array[String]): Unit = {
    val formula = """ u\cdot v = 0 \qquad \Longleftrightarrow \qquad \| u \|^2 + \|v\|^2 = \|u-v\|^2 \ . """
    val words = formula.split("\\s|\\\\").filterNot(_.isEmpty)
    val display1 = words.map(" [" + _ + "] ").mkString("")
    println(display1)
    val encoder = Base64.getEncoder
    val encoded = words.map(w => encoder.encode(w.getBytes)).map(new String(_))
    //    encoded.foreach(println)
    val display2 = encoded.mkString(" ")

    val mdWords = words.map(md5)
    val display3 = mdWords.mkString(" ")
    println(display3)
  }

  def md5(s: String): String = {
    md.update(s.getBytes)
    val digest = md.digest
    val no1 = new BigInteger(1, digest)
    val letters = no1.toString(16).toCharArray
      .map(c => if (Character.isDigit(c)) (c + 'A' - '0').toChar else c)
    "CSX" + new String(letters).toUpperCase
  }

}

// [u]  [cdot]  [v]  [=]  [0]  [qquad]  [Longleftrightarrow]  [qquad]  [|]  [u]  [|^2]  [+]  [|v]  [|^2]  [=]  [|u-v]  [|^2]  [.]
