trait Display[A] {
  def display(a: A): String
}

object Display {

  given stringDisplay: Display[String] with {
    def display(input: String): String = input
  }

  given intDisplay: Display[Int] with {
    def display(input: Int): String = input.toString
  }

  given catDisplay: Display[Cat] with {
    def display(input: Cat): String = {
      val name = Display.display(input.name)
      val age = Display.display(input.age)
      val colour = Display.display(input.colour)
      s"$name is a $age year-old $colour cat."
    }
  }

  def display[A](value: A)(using d: Display[A]): String = d.display(value)

  def print[A](value: A)(using d: Display[A]): Unit = println(display(value))
}

object DisplaySyntax {
  extension [A](value: A)(using p: Display[A]) {
    def display: String = p.display(value)
    def print: Unit = println(p.display(value))
  }
}