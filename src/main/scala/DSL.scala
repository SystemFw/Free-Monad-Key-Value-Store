package com.github.kvs
import scalaz.Free

object DSL {
  sealed trait KVS[K, V, A]
  object KVS {
    final case class Put[K, V](k: K, v: V) extends KVS[K, V, Unit]
    final case class Get[K, V](k: K) extends KVS[K, V, Option[V]]
  }

  // This instance cannot be inferred, will be fixed in scalaz 7.3
  implicit def kvsMonad[K, V] = Free.freeMonad[KVS[K, V, ?]]

  def put[K, V](k: K, v: V): Free[KVS[K,V,?], Unit] =
    Free.liftF[KVS[K, V, ?], Unit](KVS.Put(k, v))

  def get[K, V](k: K): Free[KVS[K,V,?], Option[V]] =
    Free.liftF[KVS[K, V, ?], Option[V]](KVS.Get(k))
}
