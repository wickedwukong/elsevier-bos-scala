package com.elsevier.bos.fahrenheittocelsius.exercise

import com.elsevier.bos.fahrenheittocelsius.exercise.Converter.convertFahrenheitToCelsiusIO

object Converter {
  import Console._

  def fahrenheitToCelsius(temperatureInFahrenheit: Double) = (temperatureInFahrenheit - 32) * 5.0 / 9.0

  def convertFahrenheitToCelsiusIO = for {
    _ <- PrintLine("Enter a temperature in degrees Fahrenheit:")
    doubleValue <- ReadLine.map(_.toDouble)
    _ <- PrintLine(fahrenheitToCelsius(doubleValue).toString)
  } yield ()
}

object FahrenheitToCelsiusWithIOApp extends App {
  convertFahrenheitToCelsiusIO.run
}

