package com.elsevier.bos.monad

import cats.Functor

/*
Monad law
1. Left identity:(pure(a) flatMap f) == f(a)
2. Right identity:(m flatMap pure)==m
3. Associativity: (m flatMap f flatMap g) == (m flatMap (x => f(x) flatMap g))
*/

trait Monad[F[_]] {
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  def pure[A](a: A): F[A]
}

object MonadDemo extends App {
  println("hello")
}

object BuiltInMonad extends App {
  val first = List(1, 2, 3)
  val next = List(4, 5, 6)
  val last = List(7)

  val listCombined = for {
    itemInFirstList <- first
    itemInNextList <- next
    itemInLastList <- last
  } yield(itemInFirstList * itemInNextList * itemInLastList)

  println(s"list items combined: $listCombined")
}


