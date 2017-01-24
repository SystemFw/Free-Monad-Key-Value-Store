package com.github.kvs

import scalaz.syntax.monad._

object ClientCode {
  import DSL._
  import Interpreters._

  // Programs can be built with for comprehensions
  val prog = for {
    _ <- put("a", "b")
    res <- get("a")
  } yield res.map(_.toUpperCase()).getOrElse("None")

  // Or with applicative/monadic combinators
  // Executes prog and then access the state
  val prog2 = prog *> get("a")

  //at the end of the world...
  val res: String = PureInterpreter.run(prog)(Map.empty)._2
}
