package com.elsevier.bos.monad

import cats.Functor

/*
Monad law
1. Left identity:(pure(a) flatMapf) == f(a)
2. Right identity:(m flatMap pure)==m
3. Associativity: (m flatMap f flatMap g) == (m flatMap (x => f(x) flatMap g))
*/

trait Monad[F[_]] {
  def flatMap[A, B](f: A => F[B]): F[B]
  def pure[A](a: A): F[A]
}

object MonadDemo extends App {
  println("hello")
}


