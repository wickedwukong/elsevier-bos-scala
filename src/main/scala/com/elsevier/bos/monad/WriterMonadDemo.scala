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

  def composeWriter = {
    val logAndComputation = for {
      a <- Writer.value[Vector[String], Int](99)
      _ <- Writer.tell(Vector("a", "b", "c"))
      b <- Writer(Vector("x", "y", "z"), 1)
    } yield a + b

    logAndComputation
  }

  println(s"transformWriter: ${composeWriter.run}")


  def composeWriterUsingImplicit = {
    type Logged[A] = Writer[Vector[String], A]
    import cats.syntax.applicative._

    import cats.syntax.writer._

    val writer1 = for {
      a <- 10.pure[Logged]
      _ <- Vector("a", "b", "c").tell
      b <- 32.writer(Vector("x", "y", "z"))
    } yield a + b
  }

}
