package edu.illinois.mcsds.cs410.codesearch

import java.util.Base64
import collection.JavaConverters._
object SimpleLatexParser {

  def main(args: Array[String]): Unit ={
    val formula = """ u\cdot v = 0 \qquad \Longleftrightarrow \qquad \| u \|^2 + \|v\|^2 = \|u-v\|^2 \ . """
    val words = formula.split("\\s|\\\\").filterNot(_.isEmpty)
    val display1 = words.map(" [" + _ + "] ").mkString("")
    println(display1)
    val encoder = Base64.getEncoder
    val encoded = words.map(w => encoder.encode(w.getBytes)).map(new String(_))
//    encoded.foreach(println)
    val display2 = encoded.mkString(" ")
    println(display2)
  }

}

// [u]  [cdot]  [v]  [=]  [0]  [qquad]  [Longleftrightarrow]  [qquad]  [|]  [u]  [|^2]  [+]  [|v]  [|^2]  [=]  [|u-v]  [|^2]  [.]
