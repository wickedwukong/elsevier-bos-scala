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
}

