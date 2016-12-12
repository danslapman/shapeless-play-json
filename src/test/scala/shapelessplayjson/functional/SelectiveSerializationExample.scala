package shapelessplayjson.functional

import org.scalatest.{FunSuite, Matchers}
import play.api.libs.json._
import play.api.libs.functional.syntax._
import shapeless._
import shapeless.ops.record._
import Selector._
import shapeless.syntax.singleton._

class SelectiveSerializationExample extends FunSuite with Matchers {
  private implicit def selectiveWrites[R <: HList]
    (implicit
      author: Selector.Aux[R, Witness.`'author`.T, String],
      title: Selector.Aux[R, Witness.`'title`.T, String],
      id: Selector.Aux[R, Witness.`'id`.T, Int]
    ) = (
      (__ \ "author").write[String] ~
      (__ \ "title").write[String] ~
      (__ \ "id").write[Int]
    ) ((r: R) => (author(r), title(r), id(r)))

  test("Serialize only selected fields") {
    val book =
      ('author ->> "Benjamin Pierce") ::
      ('title  ->> "Types and Programming Languages") ::
      ('id     ->>  262162091) ::
      ('price  ->>  44.11) ::
      HNil

    Json.toJson(book) shouldBe Json.obj(
      "author" -> JsString("Benjamin Pierce"),
      "title" -> JsString("Types and Programming Languages"),
      "id" -> JsNumber(262162091)
    )
  }
}
