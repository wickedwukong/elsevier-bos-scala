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

case class State[S, A](f: S => (S, A)) {
  def run(s: S): (S, A) = f(s)

  def flatMap(p: S => State[S, A]): State[S, A] = {
    val ff: S => (S, A) = {
      s => {
        val (s1, value) = f(s)
        val p1: State[S, A] = p(s1)
        val y: (S, A) = p1.run(s1)
        y
      }
    }
    new State(ff)
  }

  def map[B](ff: A => B): State[S, B] = {
    val pp: S => (S, B) = s => {
      val (newState, newValue) = f(s)
      (newState, ff(newValue))
    }

    new State(pp)
  }
}

object StateDemo extends App {
  val f: Int => (Int, String) = i => {
    val newState = i + 1
    (newState, s"The first state. The new state is $newState")
  }

  val firstState: State[Int, String] = State(f)

  val p: Int => State[Int, String] = i => State(i => {
    val newState = i * 2
    val result = s"The second state. The new State is $newState"
    (newState, result)
  })

  println(firstState.flatMap(p).run(10))

  val secondState: State[Int, String] = State(i => {
    val newState = i * 2
    val result = s"The second state. The new State is $newState"
    (newState, result)
  })

  val firstAndSecondState = for {
    a <- firstState
    b <- secondState
  } yield (b)

  println(firstAndSecondState.run(10))
}








