package com.elsevier.bos.monoids

object MonoidDemo extends App{
  import MyMonoid._

  def add[T](items: List[T])(implicit monoid: MyMonoid[T]): T = {
    items.foldLeft(monoid.empty)(monoid.combine)
  }

//  val sum = List(1,2,3).foldLeft(intMonoid.empty)(intMonoid.combine)
  val sum = add(List(1,2,3))
  println(sum)

//  val concatenation = List("h","e","l", "l", "o").foldLeft(stringMonoid.empty)(stringMonoid.combine)
  val concatenation = add(List("h","e","l", "l", "o"))

  println(concatenation)

//  what about option monoid?
  val optionIntSum = add[Option[Int]](List(Some(1), Some(2), Some(3), None, Some(5)))
  println(optionIntSum)

  val optionStringSum = add[Option[String]](List(Some("h"), Some("e"), Some("l"), None, Some("o")))
  println(optionStringSum)


  //let's come back to our map problem

//  val mapValues: Map[String, Int] = add(List(Map("a" -> 1, "b" -> 2), Map("a" -> 1, "c" -> 2), Map("b" -> 3, "d" -> 0)))


//  Map("a" -> 2, "b" -> 5, "c" -> 2, "d" -> 0)
//  println(mapValues)

}
