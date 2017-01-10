package com.elsevier.bos.monad

import cats.Functor

object TypeConstructor {
  def myMethod[F[_]] = {
    val functor = Functor.apply[F]
  }
}
