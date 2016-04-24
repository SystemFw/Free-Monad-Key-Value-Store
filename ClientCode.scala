package com.github.kvs

import scalaz.syntax.monad._

object ClientCode {
  import DSL._
  import Interpreters._

  val prog: KVSProgram[String, String, String] = for {
    _ <- put("a", "b")
    res <- get("a")
  } yield res.map(_.toUpperCase()).getOrElse("None")

  // Executes prog and then access the state
  val prog2 = prog *> get("a")

  //immutable map
  val pureTest = Map("c" -> "d")
  val pureTestRes = PureInterpreter.run(prog)(pureTest)
  val pureTestRes2 = PureInterpreter.run(prog2)(pureTest)
  // prog and prog2 share purely functional state,
  // the original map is left untouched, easy to reason about

  // mutable (shared) map
  val impureTest = scala.collection.mutable.Map("c" -> "d")
  val impureTestRes = ImpureInterpreter.run(prog)(impureTest)
  val impureTestRes2 = ImpureInterpreter.run(prog2)(impureTest)
  // prog1 and prog2 share a piece of mutable global state,
  // in this case a Map but most commonly a database
  // impureTest will change at every call, be warned!

}
