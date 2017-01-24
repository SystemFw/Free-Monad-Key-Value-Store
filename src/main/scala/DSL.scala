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
  type KVSProgram[K, V, R] = Free[({type L[A] = KVS[K,V,A]})#L, R]

  // This instance cannot be inferred, will be fixed in scalaz 7.3
  implicit def kvsMonad[K, V] = Free.freeMonad[({type L[A] = KVS[K,V,A]})#L]

  def put[K, V](k: K, v: V): KVSProgram[K, V, Unit] =
    Free.liftF[({type L[A] = KVS[K,V,A]})#L, Unit](KVS.Put(k, v))

  def get[K, V](k: K): KVSProgram[K, V, Option[V]] =
    Free.liftF[({type L[A] = KVS[K,V,A]})#L, Option[V]](KVS.Get(k))
}
