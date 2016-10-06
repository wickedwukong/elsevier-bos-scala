package com.elsevier.bos.monoids

import com.elsevier.bos.monoids.MonoidExtra.{MyMonoid, mapMergeMonoid}

object MonoidExtra {

  trait MyMonoid[T] {
    def empty: T

    def combine(t1: T, t2: T): T
  }

  val intAddition: MyMonoid[Int] = new MyMonoid[Int] {
    def combine(x: Int, y: Int) = x + y
    val empty = 0
  }

  def mapMergeMonoid[K, V](V: MyMonoid[V]): MyMonoid[Map[K, V]] = new MyMonoid[Map[K, V]] {
    def empty = Map[K, V]()

    def combine(a: Map[K, V], b: Map[K, V]) =
      (a.keySet ++ b.keySet).foldLeft(empty) { (acc, k) =>
        acc.updated(k, V.combine(a.getOrElse(k, V.empty),
          b.getOrElse(k, V.empty)))
      }
  }
}

object MonoidExtraApp extends App {
  val M: MyMonoid[Map[String, Map[String, Int]]] = {
    mapMergeMonoid(mapMergeMonoid(MonoidExtra.intAddition))
  }

  val m1 = Map("o1" -> Map("i1" -> 1, "i2" -> 2))
  val m2 = Map("o1" -> Map("i2" -> 3))

  println(M.combine(m1, m2))

}