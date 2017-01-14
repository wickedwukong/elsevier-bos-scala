package com.elsevier.bos.monad

trait Maybe[+A] {
  def flatMap[B](f: A => Maybe[B]): Maybe[B]
}

case class Just[+A](a: A) extends Maybe[A] {
  override def flatMap[B](f: (A) => Maybe[B]): Maybe[B] = f(a)
}

case object MaybeNot extends Maybe[Nothing] {
  override def flatMap[B](f: (Nothing) => Maybe[B]): Maybe[B] = MaybeNot
}

object MaybeDemo extends App {

  //Just(1) * Just(2) * Just(3)

  val value: Maybe[Int] = Just(1).flatMap(a => Just(2).flatMap(b => Just(3).flatMap(c => Just(a * b * c))))

  println(value)

//  val value2 = for {
//    a <- Just(1)
//    b <- Just(2)
//    c <- Just(3)
//  } yield (a * b * c)
//
//  println(value2)
    
}
