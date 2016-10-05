package com.elsevier.bos.typeclass


sealed trait Json
final case class JsObject(get: Map[String,Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
final case class Person(name: String, email: String)

//the type class itself, instances for particular types, and the interface methods that we expose to users.
trait JsonWriter[A] {
  def write(value: A): Json
}

object DefaultJsonWriters {
  implicit val stringJsonWriter = new JsonWriter[String] {
    def write(value: String): Json = JsString(value)
  }

  implicit val personJsonWriter = new JsonWriter[Person] {
    def write(value: Person): Json =
      JsObject(Map("name" -> JsString(value.name), "email" -> JsString(value.email)))
  }
}

//An interface is any functionality we expose to users.
// Interfaces to type classes are generic methods that accept instances of the type class as implicit parameters.
//There are two common ways of specifying an interface: Interface Objects and Interface Syntax.

//Interface Objects
object Json {
  def toJson[A](value: A)(implicit writer: JsonWriter[A]): Json =
    writer.write(value)
}

//Interface Syntax
object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit writer: JsonWriter[A]): Json = { writer.write(value)
    } }
}

object TypeClassDemo extends App{
  import DefaultJsonWriters._
  val personJson: Json = Json.toJson(Person("Bryn", "Bryn@example.com"))

  println(personJson)

  val stringJson: Json = Json.toJson("Hello BOS team")

  println(stringJson)

//  This does not work
//  val intJson: Json = Json.toJson(888)
//  println(intJson)


//  Use interface syntax
//  import JsonSyntax._
//  println(Person("Stuart", "Stuart@example.com").toJson)

}
