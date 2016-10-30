package com.elsevier.bos.fahrenheittocelsius.exercise
import cats.Eval
import cats.Eval._

object ConsoleUsingCatsEvalMonad {
  def ReadLine = Eval.always{ io.StdIn.readLine() }
  def PrintLine(message: String) = Eval.always{scala.Console.println(message) }
}

object FahrenheitToCelsiusWithCatsEvalMonad extends App{

  import ConsoleUsingCatsEvalMonad._

  def fahrenheitToCelsius(temperatureInFahrenheit: Double) = (temperatureInFahrenheit - 32) * 5.0 / 9.0

  val fahrenheitToCelsiusMonad = for {
    _ <- PrintLine("Enter a temperature in degrees Fahrenheit:")
    doubleValue <- ReadLine.map(_.toDouble)
    _ <- PrintLine(fahrenheitToCelsius(doubleValue).toString)
  } yield ()

  println(fahrenheitToCelsiusMonad)

  fahrenheitToCelsiusMonad.value
}
