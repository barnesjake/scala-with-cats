@main def MonoidsExercise(): Unit = {

  trait Semigroup[A] {
    def combine(x: A, y: A): A
  }

  trait Monoid[A] extends Semigroup[A] {
    def empty: A
  }

  object Monoid {
    def apply[A](implicit monoid: Monoid[A]) =
      monoid
  }

  //and, or, exor, exnor
  given booleanAndMonoid: Monoid[Boolean] with {
    def combine(x: Boolean, y: Boolean): Boolean = x && y

    def empty: Boolean = true
  }
  given booleanOrMonoid: Monoid[Boolean] with {
    def combine(x: Boolean, y: Boolean): Boolean = x || y

    def empty: Boolean = false
  }
  given booleanExclusiveOrMonoid: Monoid[Boolean] with {
    def combine(x: Boolean, y: Boolean): Boolean = (!x && y) || (x && !y)

    def empty: Boolean = false
  }
  given booleanExclusiveNorMonoid: Monoid[Boolean] with {
    def combine(x: Boolean, y: Boolean): Boolean = (!x || y) && (x || !y)

    def empty: Boolean = true
  }

  //sets
  given setUnionMonoid[A]: Monoid[Set[A]] with {
    def combine(x: Set[A], y: Set[A]): Set[A] = x.union(y)

    def empty: Set[A] = Set.empty[A]
  }

  val intSetMonoid: Monoid[Set[Int]] = Monoid[Set[Int]]
  println(intSetMonoid.combine(Set(1, 2, 3), Set(3, 4, 5)))
  println(intSetMonoid.empty)
  val stringSetMonoid: Monoid[Set[String]] = Monoid[Set[String]]
  println(stringSetMonoid.combine(Set("hel", "lo"), Set("the", "re")))

  given setIntersectionSemiGroup[A]: Semigroup[Set[A]] with {
    def combine(x: Set[A], y: Set[A]): Set[A] = x.intersect(y)
  }
}

@main def MonoidsFurther(): Unit = {

  import cats.Monoid
  import cats.Semigroup

  println(Monoid[String].combine("Hi ", "there"))
  println(Semigroup[String].combine("Hi ", "there"))

  import cats.syntax.all.*
  val stringResult = "Hi " |+| "there" |+| Monoid[String].empty
  val intResult = 1 |+| 2 |+| Monoid[Int].empty
  println(stringResult)
  println(intResult)

  def add[A](items: List[A])(using monoid: Monoid[A]): A = items.foldLeft(monoid.empty)(_ |+| _)

  //or use context bounds
  def add2[A: Monoid](items: List[A]): A = items.foldLeft(Monoid.empty[A])(_ |+| _)

  println(add(List(Some(1), Some(2), None, Some(3))))
  println(add(List(1, 2, 3)))
  println(add2(List(Some(1), Some(2), None, Some(3))))
  println(add2(List(1, 2, 3)))


  final case class Order(totalCost: Double, quantity: Double)
  given orderMonoid: Monoid[Order] with {
    def combine(x: Order, y: Order): Order = Order(x.totalCost + y.totalCost , x.quantity + y.quantity)

    def empty: Order = Order(0.0, 0.0)
  }

  println(Order(2.0, 1.0) |+| Order(2.0, 1.0))
  println(Order(2.0, 2.0) |+| Order(2.0, 1.0))

  println(add(List(Order(2.0,2.0), Order(2.0,1.0))))

}
