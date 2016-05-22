package com.github.kvs

import org.scalatest._
import Interpreters.{PureInterpreter, ImpureInterpreter}
import ClientCode.{prog,prog2}

class ClientCodeSpec extends WordSpec with Matchers {

  "The KVS algebra" when {
    "interpreted purely" should {
      "allow writing programs that use a key value store" in {
        PureInterpreter.run(prog)(Map.empty)._2 shouldBe "B"
      }

      "allow sharing the key value store state between different programs" in {
        PureInterpreter.run(prog2)(Map.empty)._2 shouldBe Some("b")
      }

      "Never mutate a key value store" in {
        val kvs: Map[String,String] = Map.empty
        PureInterpreter.run(prog)(kvs)
        kvs shouldBe empty
      }
    }

    "interpreted impurely" should {
      import collection.mutable.Map

      "allow writing programs that use a key value store" in {
        ImpureInterpreter.run(prog)(Map.empty) shouldBe "B"
      }

      "allow sharing the key value store state between different programs" in {
        ImpureInterpreter.run(prog2)(Map.empty) shouldBe Some("b")
      }

      "mutate a key value store if programs do so" in {
        val kvs: Map[String,String] = Map.empty
        ImpureInterpreter.run(prog)(kvs)
        kvs should not be empty
      }
    }
  }

}
