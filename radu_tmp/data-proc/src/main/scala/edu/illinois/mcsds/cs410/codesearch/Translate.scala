package edu.illinois.mcsds.cs410.codesearch

import scala.collection.mutable

object Translate {
  def main(args: Array[String]): Unit = {
    println("c2s = " + pyEncode)
    println("s2c = " + pyDecode)
  }

  def pyEncode: String = punctuation.map(c_s => s""" "${c_s._1}": "${c_s._2}" """).mkString("{", ",", "}")

  def pyDecode: String = punctuation.map(c_s => s""" "${c_s._2}": "${c_s._1}" """).mkString("{", ",", "}")

  def encoding: Map[Char, String] = punctuation.toMap

  def decoding: Map[String, Char] = punctuation.map(c_s => (c_s._2, c_s._1)).toMap

  private def punctuation: Seq[(Char, String)] = {
    val pc = new mutable.ArrayBuffer[(Char, String)]
    var d1 = 'A'
    var d2 = 'A'
    for {
      c <- ' ' to '~'
    } {
      if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
        val d1_d2 = inc(d1, d2)
        d1 = d1_d2._1
        d2 = d1_d2._2
        pc += ((c, s" CSX$d1$d2 "))
      }
    }
    pc
  }

  private def inc(a: Char, b: Char): (Char, Char) = {
    if (b == 'Z') ((a + 1).toChar, 'A') else (a, (b + 1).toChar)
  }

}
