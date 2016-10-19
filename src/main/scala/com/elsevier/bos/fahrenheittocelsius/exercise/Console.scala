package com.elsevier.bos.fahrenheittocelsius.exercise

object Console {
  def ReadLine = IO { io.StdIn.readLine() }
  def print(message: String) = IO { scala.Console.print(message) }
  def PrintLine(message: String) = IO { scala.Console.println(message) }
}
