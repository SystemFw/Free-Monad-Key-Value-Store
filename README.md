# Free Monad KVS
This project provides a minimal working example on how to build a free monad kvs that is parametric on both the key and the value type in Scala (something I could not find anywhere else).

It provides a simple *put* and *get* DSL and two interpreters, one based on the State monad and an immutable map, and the other on the IO monad and a mutable map (normally this would be a database). Have a look at the tests to see an example of usage.

## Dependencies
I am using [Scalaz 7.2.2](https://github.com/scalaz/scalaz), which introduces significant changes to Free, making it more similar to the Operational Monad by using a GADT encoding, and removing the need to use Coyoneda explicitly.

This branch implements the KVS in vanilla scala, i.e with explicit type lambdas and various type synonims/type annotations to work around SI-2712.
