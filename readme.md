shapeless-play-json [![Release](https://jitpack.io/v/danslapman/shapeless-play-json.svg)](https://jitpack.io/#danslapman/shapeless-play-json)
=========

**shapeless-play-json** provides JSON (un)marshalling support for [shapeless](https://github.com/milessabin/shapeless)'s extensible records via play-json.
Here is a brief usage example:
```scala
import play.api.libs.json._
import shapeless._
import shapeless.record._
import shapeless.syntax.singleton._
import shapelessplayjson._
import shapelessplayjson.auto._

val book =
    ('author ->> "Benjamin Pierce") ::
    ('title  ->> "Types and Programming Languages") ::
    ('id     ->>  262162091) ::
    ('price  ->>  44.11) ::
    HNil
  val book2 = book + ('tag ->> "programming")
  val bookJson = Json.toJson(book2)
  val bookJsonStr = Json.stringify(bookJson)
  println(bookJsonStr)

  type Book = Record.`'author -> String, 'title -> String, 'id -> Int, 'price -> Double`.T
  val deserializedBook = Json.parse(bookJsonStr).as[Book]
  println(deserializedBook)
```

shapeless-play-json is available via jitpack:
```
    resolvers += "jitpack" at "https://jitpack.io"

    libraryDependencies += "com.github.danslapman" %% "shapeless-play-json" % "{version}"
```