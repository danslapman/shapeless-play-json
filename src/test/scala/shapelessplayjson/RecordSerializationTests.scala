package shapelessplayjson

import play.api.libs.json._
import shapeless._
import shapeless.syntax.singleton._
//import shapeless.record._

import org.scalatest.{FunSuite, Matchers}

class RecordSerializationTests extends FunSuite with Matchers {
  test("Serialize HNil") {
    Json.toJson(HNil) shouldBe Json.obj()
  }

  test("Serialize record instance") {
    val book =
      ('author ->> "Benjamin Pierce") ::
      ('title  ->> "Types and Programming Languages") ::
      ('id     ->>  262162091) ::
      ('price  ->>  44.11) ::
      HNil

    Json.toJson(book) shouldBe Json.obj(
      "author" -> JsString("Benjamin Pierce"),
      "title" -> JsString("Types and Programming Languages"),
      "id" -> JsNumber(262162091),
      "price" -> JsNumber(44.11)
    )
  }
}
