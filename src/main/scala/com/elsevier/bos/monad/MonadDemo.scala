package com.elsevier.bos.monad

import cats.Functor

trait Monad[F[_]] {
  def flatMap[A, B](f: A => F[B]): F[B]
  def unit[A](a: A): F[A]
}

object MonadDemo {


}


