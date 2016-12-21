package shapelessplayjson.strict

import play.api.libs.json._
import shapeless._
import shapeless.syntax.singleton._
import shapeless.ops.hlist.RightFolder
import RightFolder._
import shapelessplayjson.hNilReads

import org.scalatest.{FunSuite, Matchers}

import scala.language.higherKinds

class RecordDeserializationTests extends FunSuite with Matchers {

  val jso = Json.obj(
    "author" -> JsString("Benjamin Pierce"),
    "title" -> JsString("Types and Programming Languages"),
    "id" -> JsNumber(262162091),
    "price" -> JsNumber(44.11)
  )

  val schema =
    ('author ->> (__ \ 'author).read[String]) ::
    ('title ->> (__ \ 'title).read[String]) ::
    ('id ->> (__ \ 'id).read[Int]) ::
    ('price ->> (__ \ 'price).read[Double]) ::
    HNil

  val reader = schema.map(SchemaConverter).foldRight(hNilReads.reads _)(ReadsComposer)

  test("Deserialize record") {
    val result = reader(jso)
    result shouldBe 'success
    val book = result.get

    book shouldBe ('author ->> "Benjamin Pierce") ::
      ('title  ->> "Types and Programming Languages") ::
      ('id     ->>  262162091) ::
      ('price  ->>  44.11) ::
      HNil
  }
}
