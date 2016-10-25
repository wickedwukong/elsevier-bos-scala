package com.elsevier.bos.monoids

trait MyMonoid[T] {
  def empty: T

  def combine(t1: T, t2: T): T
}

object MyMonoid {
  def intMonoid = new MyMonoid[Int] {
    override def empty: Int = 0

    override def combine(t1: Int, t2: Int): Int = t1 + t2
  }

  def stringMonoid = new MyMonoid[String] {
    override def empty: String = ""

    override def combine(t1: String, t2: String): String = t1 + t2
  }

  def optionStringMonoid = new MyMonoid[Option[String]] {

    override def empty: Option[String] = None

    override def combine(t1: Option[String], t2: Option[String]): Option[String] = {      (t1, t2) match {
      case (Some(x), Some(y)) => Some(stringMonoid.combine(x, y))
      case (Some(x), None) => Some(stringMonoid.combine(x, stringMonoid.empty))
      case (None, Some(y)) => Some(stringMonoid.combine(stringMonoid.empty, y))
      case (None, None) => Some(stringMonoid.combine(stringMonoid.empty, stringMonoid.empty))
    }
    }
  }

  def optionIntMonoid = new MyMonoid[Option[Int]] {
    override def empty: Option[Int] = None

    override def combine(t1: Option[Int], t2: Option[Int]): Option[Int] = {
      (t1, t2) match {
        case (Some(x), Some(y)) => Some(intMonoid.combine(x, y))
        case (Some(x), None) => Some(intMonoid.combine(x, intMonoid.empty))
        case (None, Some(y)) => Some(intMonoid.combine(intMonoid.empty, y))
        case (None, None) => Some(intMonoid.combine(intMonoid.empty, intMonoid.empty))
      }
    }
  }
}

