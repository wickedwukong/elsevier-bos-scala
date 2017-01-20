package com.elsevier.bos.monad

object WriterMonadDemo extends App {
  import cats.data.Writer
  import cats.instances.vector._
  val logAndValueWriter = Writer(Vector("It all starts here."), 123)

  val log = logAndValueWriter.written

  println(s"written: $log")

  val value = logAndValueWriter.value

  println(s"value: $value")

  val logAndValue: (Vector[String], Int) = logAndValueWriter.run

  println(s"logAndValue: $logAndValue")


}
