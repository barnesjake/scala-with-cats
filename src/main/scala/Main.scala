import DisplaySyntax.*

@main def run(): Unit = {
  val cat = Cat("Marley", 12, "tabby")
  Display.print(cat)

  //using DisplaySyntax
  cat.print

  //extension methods for other types
  123.print
  "hello".print

//  Throws No given instance of type Display[Long], i.e. no type class instance defined in our Display
//  123L.print
}

final case class Cat(name: String, age: Int, colour: String)
