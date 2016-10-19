package com.elsevier.bos.fahrenheittocelsius.exercise

import java.io._

import scala.util.Try

trait IO[A] {
  self =>

  def run: A

  def map[B](f: A => B): IO[B] = new IO[B] {
    def run = f(self.run)
  }

  def flatMap[B](f: A => IO[B]): IO[B] = new IO[B] {
    override def run: B = f(self.run).run
  }
}

object IO {
  def apply[A](a: => A) = new IO[A] {
    override def run: A = a
  }
}

