package com.elsevier.bos.monoids

object MonoidDemo extends App{

  import MyMonoid._
  
  val sum = List(1,2,3).foldLeft(intMonoid.empty)(intMonoid.combine)

  println(sum)

  val concatenation = List("h","e","l", "l", "o").foldLeft(stringMonoid.empty)(stringMonoid.combine)

  println(concatenation)


  //what about combine a list of maps?

//  val mapValues: Map[String, Int] = List(Map("a" -> 1, "b" -> 2), Map("a" -> 1, "c" -> 2), Map("b" -> 3, "d" -> 0)) {
//    (acc, map) => ???
//  }
//
//
  //Map("a" -> 2, "b" -> 5, "c" -> 2, "d" -> 0)
//  println(mapValues)

}
