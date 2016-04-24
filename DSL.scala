package com.github.kvs
import scalaz.Free

object DSL {

  sealed trait KVS[K, V, A]
  object KVS {
    final case class Put[K, V](k: K, v: V) extends KVS[K, V, Unit]
    final case class Get[K, V](k: K) extends KVS[K, V, Option[V]]
  }

  // This type synonym is necessary to aid type inference
  // for higher kinded types and make monad combinators work
  // see scala bugs SI-6895 and SI-2712.
  type KVSProgram[K, V, R] = Free[KVS[K, V, ?], R]
  // This instance cannot be inferred
  implicit def kvsMonad[K, V] = Free.freeMonad[KVS[K, V, ?]]

  def put[K, V](k: K, v: V): KVSProgram[K, V, Unit] =
    Free.liftF[KVS[K, V, ?], Unit](KVS.Put(k, v))

  def get[K, V](k: K): KVSProgram[K, V, Option[V]] =
    Free.liftF[KVS[K, V, ?], Option[V]](KVS.Get(k))
}
