package shapelessplayjson.strict

import play.api.libs.json._
import shapeless._
import shapeless.syntax.singleton._
import shapelessplayjson.hNilReads
import shapeless.ops.hlist.RightFolder._
import org.scalatest.{FunSuite, Matchers}
import shapeless.record.Record

import scala.language.higherKinds

class RecordDeserializationTests extends FunSuite with Matchers {

  type Book = Record.`'author -> String, 'title -> String, 'id -> Long, 'price -> Double`.T

  val jso = Json.obj(
    "author" -> JsString("Benjamin Pierce"),
    "title" -> JsString("Types and Programming Languages"),
    "id" -> JsNumber(262162091),
    "price" -> JsNumber(44.11)
  )

  val schema =
    ('author ->> (__ \ 'author).read[String]) ::
    ('title ->> (__ \ 'title).read[String]) ::
    ('id ->> (__ \ 'id).read[Long]) ::
    ('price ->> (__ \ 'price).read[Double]) ::
    HNil

  implicit val bookReads: Reads[Book] =
    (json: JsValue) => (schema.map(SchemaConverter).foldRight(hNilReads.reads _)(ReadsComposer) _) (json)

  test("Deserialize record") {
    val result = jso.validate[Book]
    result shouldBe 'success
    val book = result.get

    book shouldBe ('author ->> "Benjamin Pierce") ::
      ('title  ->> "Types and Programming Languages") ::
      ('id     ->>  262162091) ::
      ('price  ->>  44.11) ::
      HNil
  }
}
