package shapelessplayjson.strict

import play.api.libs.json._
import shapeless._
import shapeless.syntax.singleton._

import org.scalatest.{FunSuite, Matchers}

class RecordSerializationTests extends FunSuite with Matchers {
  object BookFormat extends PolyWrites {
    implicit def author = write[Witness.`'author`.T, String]((__ \ "author").write[String])
    implicit def title = write[Witness.`'title`.T, String]((__ \ "title").write[String])
    implicit def id = write[Witness.`'id`.T, Int]((__ \ "id").write[Int])
    implicit def price = write[Witness.`'price`.T, Double]((__ \ "price").write[Double])
  }

  val book =
    ('author ->> "Benjamin Pierce") ::
    ('title  ->> "Types and Programming Languages") ::
    ('id     ->>  262162091) ::
    ('price  ->>  44.11) ::
    HNil

  test("Serialize record") {
    import BookFormat.writes

    Json.toJson(book) shouldBe Json.obj(
      "author" -> JsString("Benjamin Pierce"),
      "title" -> JsString("Types and Programming Languages"),
      "id" -> JsNumber(262162091),
      "price" -> JsNumber(44.11)
    )
  }
}
