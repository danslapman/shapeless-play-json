package shapelessplayjson.strict

import org.scalatest.{FunSuite, Matchers}
import play.api.libs.json._
import shapelessplayjson._
import shapeless._
import shapeless.record._
import shapeless.syntax.singleton._

class RecordSerializationTests extends FunSuite with Matchers {
  private type Book = Record.`'author -> String, 'title -> String, 'id -> Long, 'price -> Double`.T

  private val schema =
    ('author ->> (__ \ "author").write[String]) ::
    ('title ->> (__ \ "title").write[String]) ::
    ('id ->> (__ \ "id").write[Int]) ::
    ('price ->> (__ \ "price").write[Double]) ::
    HNil

  //private implicit val bookWrites = strictWrites(schema)

  test("Serialize with strict schema") {
    /*
    val book =
      ('author ->> "Benjamin Pierce") ::
      ('title  ->> "Types and Programming Languages") ::
      ('id     ->>  262162091) ::
      ('price  ->>  44.11) ::
      HNil

    strictWrites(schema)(book) shouldBe Json.obj(
      "author" -> JsString("Benjamin Pierce"),
      "title" -> JsString("Types and Programming Languages"),
      "id" -> JsNumber(262162091),
      "price" -> JsNumber(44.11)
    )*/
  }
}
