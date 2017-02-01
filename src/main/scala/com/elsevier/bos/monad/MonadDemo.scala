package com.elsevier.bos.monad

import scala.util.Random

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

object State {
  def sequence[S, A](sas: List[State[S, A]]): State[S, List[A]] = {
    def go(s: S, actions: List[State[S, A]], acc: List[A]): (S, List[A]) =
      actions match {
        case Nil => (s, acc.reverse)
        case h :: t => h.run(s) match {
          case (s2, a) => go(s2, t, a :: acc)
        }
      }
    State((s: S) => go(s, sas, List()))
  }
}

case class State[S, A](f: S => (S, A)) {
  def run(s: S): (S, A) = f(s)

  def flatMap[B](p: A => State[S, B]): State[S, B] = {
    val ff: S => (S, B) = {
      s => {
        val (s1, value) = f(s)
        val p1: State[S, B] = p(value)
        val y: (S, B) = p1.run(s1)
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

trait RNG {
  def nextInt: (RNG, Int)
}

case class SimpleRNG(seed: Long) extends RNG {
  def nextInt: (RNG, Int) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    val nextRNG = SimpleRNG(newSeed)
    val n = (newSeed >>> 16).toInt
    (nextRNG, n)
  }
}


object StateDemo extends App {
  val randomState = State[RNG, Int](rng => rng.nextInt)

  val randomIntGenerator = for {
    a <- randomState
    b <- randomState
    c <- randomState
  } yield (c)

  private val (finalRng, value) = randomIntGenerator.run(SimpleRNG(1L))
  println(finalRng)
  println(value)
}

object CandyMachine extends App {

  sealed trait Input

  case object Coin extends Input

  case object Turn extends Input

  case class Machine(locked: Boolean, candies: Int, coins: Int)

  def simulateMachine(inputs: List[Input]): State[Machine, (Int, Int)] = {
    val f: Machine => (Machine, (Int, Int)) = machine => {
      val finalMachine: Machine = inputs.foldLeft(machine) {
        (newMachine, input) => (input, newMachine) match {
          case (_, Machine(_, 0, _)) => newMachine
          case (Coin, Machine(false, _, _)) => newMachine
          case (Turn, Machine(true, _, _)) => newMachine
          case (Coin, Machine(true, candy, coin)) =>
            Machine(false, candy, coin + 1)
          case (Turn, Machine(false, candy, coin)) =>
            Machine(true, candy - 1, coin)
        }
      }
      (finalMachine, (finalMachine.candies, finalMachine.coins))
    }
    State(f)
  }


  val (machine, (candies, coins)) = simulateMachine(List(Coin, Turn)).run(Machine(true, 2, 1))

  println(machine)
  println(candies)
  println(coins)

}

object Candy extends App {

  import State.sequence

  sealed trait Input

  case object Coin extends Input

  case object Turn extends Input

  case class Machine(locked: Boolean, candies: Int, coins: Int)

  def update: (Input) => (Machine) => Machine = (i: Input) => (s: Machine) =>
    (i, s) match {
      case (_, Machine(_, 0, _)) => s
      case (Coin, Machine(false, _, _)) => s
      case (Turn, Machine(true, _, _)) => s
      case (Coin, Machine(true, candy, coin)) =>
        Machine(false, candy, coin + 1)
      case (Turn, Machine(false, candy, coin)) =>
        Machine(true, candy - 1, coin)
    }

  def modify[S](f: S => S): State[S, Unit] = for {
    s <- get
    _ <- set(f(s))
  } yield ()

  def get[S]: State[S, S] = State(s => (s, s))

  def set[S](s: S): State[S, Unit] = State(_ => (s, ()))

  def simulateMachine(inputs: List[Input]): State[Machine, (Int, Int)] = for {
    _ <- sequence(inputs map (modify[Machine] _ compose update))
    s <- get
  } yield (s.coins, s.candies)

  val (machine, coinAndCandies) = simulateMachine(List(Coin, Turn)).run(Machine(true, 2, 1))

  println(machine)
  println(coinAndCandies)
}







