shapeless-play-json [ ![Download](https://api.bintray.com/packages/danslapman/maven/shapeless-play-json/images/download.svg) ](https://bintray.com/danslapman/maven/shapeless-play-json/_latestVersion)
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

shapeless-play-json is available via bintray:
```
    resolvers += Resolver.bintrayRepo("danslapman", "maven")

    libraryDependencies += "danslapman" %% "shapeless-play-json" % "{version}"
```