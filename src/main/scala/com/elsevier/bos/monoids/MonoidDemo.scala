package com.elsevier.bos.monoids

object MonoidDemo extends App{

  val sum = List(1,2,3).foldLeft(0){
    (acc, value) => acc + value
  }

  println(sum)

  val concatenation = List("h","e","l", "l", "o").foldLeft(""){
    (acc, value) => acc + value
  }

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
