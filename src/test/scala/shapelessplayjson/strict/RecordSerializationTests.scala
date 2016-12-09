package shapelessplayjson.strict

import org.scalatest.{FunSuite, Matchers}
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import shapelessplayjson._
import shapeless._
import shapeless.record._
import shapeless.syntax.singleton._

class RecordSerializationTests extends FunSuite with Matchers {
  private type Book = Record.`'author -> String, 'title -> String, 'id -> Int, 'price -> Double`.T

  private val bookFormat =
    (__ \ "author").format[String] ~
    (__ \ "title").format[String] ~
    (__ \ "id").format[Int] ~
    (__ \ "price").format[Double]

  private implicit val bookWrites: Format[Book] = bookFormat(null, unapplyRecord[Book].andThen(_.tupled))

  test("Serialize with strict schema") {
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
