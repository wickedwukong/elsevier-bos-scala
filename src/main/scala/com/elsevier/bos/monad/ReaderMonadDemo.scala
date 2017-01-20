package com.elsevier.bos.monad

import cats.Id

object ReaderMonadDemo extends App {

  import cats.data.Reader

  def double(a: Int): Int = a * 2

  val doubleReader: Reader[Int, Int] = Reader(double)

  val doubleExample = doubleReader.run

  println(s"doubleExample: $doubleExample(2)")


  val mapReader: Reader[Int, Int] = doubleReader.map(_ + 1)

  println(s"mapReader exmaple: ${mapReader.run.apply(2)}")


  val add1Reader: Reader[Int, Int] = Reader((x: Int) => x + 1)

  val composedReader: Reader[Int, (Int, Int)] = for {
    x <- doubleReader
    y <- add1Reader
  } yield (x, y)

  println(s"composedReader example: ${composedReader.run.apply(2)}")
}

object DependencyInjectionReaderDemo extends App {

  import cats.data.Reader
  import cats.syntax.applicative._

  trait Database {
    def findUsername(userId: Int): Option[String]

    def findPassword(username: String): Option[String]
  }

  final case class InMemoryDatabase(users: Map[Int, String], passwords: Map[String, String]) extends Database {
    override def findUsername(userId: Int): Option[String] = users.get(userId)

    override def findPassword(username: String): Option[String] = passwords.get(username)
  }

  type DatabaseReader[A] = Reader[Database, A]

  def findUsername(userId: Int): DatabaseReader[Option[String]] = Reader {
    (database: Database) => database.findUsername(userId)
  }

  def checkPassword(username: String, password: String): DatabaseReader[Boolean] = Reader {
    (database: Database) => database.findPassword(username).filter(_ == password).isDefined
  }

  def checkLogin(userId: Int, password: String): DatabaseReader[Boolean] = {
    for {
      username <- findUsername(userId)
      isPasswordOK <- username.map{checkPassword(_, password)}.getOrElse(false.pure[DatabaseReader])
    } yield isPasswordOK
  }

  val database = InMemoryDatabase(Map(123 -> "bob", 456 -> "dave"), Map("bob" -> "top-secret", "dave" -> "less-secretive"))

  val bobLogin: (Database) => Boolean = checkLogin(123, "top-secret").run

  println(s"bobLogin: ${bobLogin(database)}")

  val daveLogin: (Database) => Boolean = checkLogin(456, "passowrd").run

  println(s"daveLogin: ${daveLogin(database)}")

}
