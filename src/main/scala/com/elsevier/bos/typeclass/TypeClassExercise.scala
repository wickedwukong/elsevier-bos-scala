package com.elsevier.bos.typeclass

//Type class
trait Printable[A] {
  def format(a: A): String
}

final case class Cat(name: String, age: Int, color: String)

//define instances - implementations
object PrintDefaults {
  //define an instance for Int
  //define an instance for Cat
}

//define the interface methods
//.....

object TypeClassExercise extends App {
  val value = 999

//  format value

  val cat = Cat("Mason", 100, "black")

  //format cat
}
