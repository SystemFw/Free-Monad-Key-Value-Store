package com.github.kvs

import scalaz.{~>, State}
import scalaz.effect.IO
import scalaz.syntax.functor._

object Interpreters {
  object PureInterpreter {
    import DSL._

    private def interpreter[K, V] =
      new (({type L[A] = KVS[K,V,A]})#L ~> ({type L[A] = State[Map[K, V], A]})#L) {
        def apply[A](fa: KVS[K, V, A]): State[Map[K, V], A] = fa match {
          case KVS.Put(k, v) => State.modify(_ + (k -> v))
          case KVS.Get(k) => State.get.map(_.get(k))
        }
      }

    def run[K, V, A](prog: KVSProgram[K, V, A])(st: Map[K, V]): (Map[K, V], A) =
      prog.foldMap[({type L[A] = State[Map[K, V], A]})#L](interpreter).run(st)
  }

  object ImpureInterpreter {
    import DSL._
    import scala.collection.mutable

    private def interpreter[K, V](m: mutable.Map[K, V]) =
      new (({type L[A] = KVS[K,V,A]})#L ~> IO) {
        def apply[A](fa: KVS[K, V, A]): IO[A] = fa match {
          case KVS.Put(k, v) => IO(m.put(k, v)).void
          case KVS.Get(k) => IO(m.get(k))
        }
      }

    def run[K, V, A](prog: KVSProgram[K, V, A])(m: mutable.Map[K, V]): A =
      prog.foldMap(interpreter(m)).unsafePerformIO()
  }

}
