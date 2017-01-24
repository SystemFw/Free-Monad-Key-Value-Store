# Free Monad KVS
This project provides a minimal working example on how to build a free monad kvs that is parametric on both the key and the value type in Scala (something I could not find anywhere else).

It provides a simple *put* and *get* DSL and two interpreters, one based on the State monad and an immutable map, and the other on the IO monad and a mutable map (normally this would be a database). Have a look at the tests to see an example of usage.

## Dependencies
I am using [Scalaz 7.2.x](https://github.com/scalaz/scalaz), which introduces significant changes to Free, making it more similar to the Operational Monad by using a GADT encoding, and removing the need to use Coyoneda explicitly. 

I also rely on two popular compiler plugins: [Kind projector](https://github.com/non/kind-projector), to make the type lambdas a bit more palatable, and the [SI-2712 fix plugin](https://github.com/milessabin/si2712fix-plugin), to avoid spurious type errors due to type inference failures. However, it's possible to make it work without these, have a look at the branch [Vanilla](https://github.com/SystemFw/Free-Monad-Key-Value-Store/tree/Vanilla) to see how it's done (Warning: it's ugly).
