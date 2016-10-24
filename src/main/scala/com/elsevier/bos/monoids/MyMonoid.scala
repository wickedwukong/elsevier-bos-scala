package com.elsevier.bos.monoids

trait MyMonoid[T] {
  def empty: T

  def combine(t1: T, t2: T): T
}

object MyMonoid {
  implicit def intMonoid = new MyMonoid[Int] {
    override def empty: Int = 0

    override def combine(t1: Int, t2: Int): Int = t1 + t2
  }

  implicit def stringMonoid = new MyMonoid[String] {
    override def empty: String = ""

    override def combine(t1: String, t2: String): String = t1 + t2
  }

  implicit def optionMonoid[A](implicit monoid: MyMonoid[A]) = new MyMonoid[Option[A]] {
    override def empty: Option[A] = None

    override def combine(t1: Option[A], t2: Option[A]): Option[A] = {
      (t1, t2) match {
        case (Some(x), Some(y)) => Some(monoid.combine(x, y))
        case (Some(x), None) => Some(monoid.combine(x, monoid.empty))
        case (None, Some(y)) => Some(monoid.combine(monoid.empty, y))
        case (None, None) => Some(monoid.combine(monoid.empty, monoid.empty))
      }
    }
  }

}

