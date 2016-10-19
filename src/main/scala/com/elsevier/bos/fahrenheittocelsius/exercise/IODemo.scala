package com.elsevier.bos.fahrenheittocelsius.exercise

object IOMonadDemo extends App {
  import Console._

  ReadLine.flatMap(PrintLine(_)).run

  val parseIntIO: IO[Int] = ReadLine.map(_.toInt)

  println(parseIntIO.run * 2)
}

