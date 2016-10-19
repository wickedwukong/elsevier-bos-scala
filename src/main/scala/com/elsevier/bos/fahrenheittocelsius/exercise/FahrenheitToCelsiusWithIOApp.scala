package com.elsevier.bos.fahrenheittocelsius.exercise


object FahrenheitToCelsiusWithIO extends App {
  def fahrenheitToCelsius(temperatureInFahrenheit: Double) = (temperatureInFahrenheit - 32) * 5.0 / 9.0

  import Console._

  val convertFahrenheitToCelsiusIO = for {
    _ <- PrintLine("Enter a temperature in degrees Fahrenheit:")
    doubleValue <- ReadLine.map(_.toDouble)
    _ <- PrintLine(fahrenheitToCelsius(doubleValue).toString)
  } yield ()

  convertFahrenheitToCelsiusIO.run
}

