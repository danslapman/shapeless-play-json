package shapelessplayjson.auto

import org.scalatest.{FunSuite, Matchers}
import play.api.libs.json._
import shapelessplayjson._
import shapeless._
import shapeless.record._

class RecordDeserializationTests extends FunSuite with Matchers {
  test("Deserialize HNil") {
    Json.obj().validate[HNil] shouldBe JsSuccess(HNil)
  }

  type Book = Record.`'author -> String, 'title -> String, 'id -> Long, 'price -> Double`.T

  test("Deserialize record type") {
    val jso = Json.obj(
      "author" -> JsString("Benjamin Pierce"),
      "title" -> JsString("Types and Programming Languages"),
      "id" -> JsNumber(262162091),
      "price" -> JsNumber(44.11)
    )

    val result = jso.validate[Book]
    result shouldBe 'success
    val book = result.get
    book('author) shouldBe "Benjamin Pierce"
    book('title) shouldBe "Types and Programming Languages"
    book('id) shouldBe 262162091
    book('price) shouldBe 44.11
  }
}
