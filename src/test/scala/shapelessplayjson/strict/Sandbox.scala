package shapelessplayjson.strict

import org.scalatest.{FunSuite, Matchers}
import play.api.libs.json._
import shapeless._
import shapeless.labelled._
import shapeless.record._
import shapeless.ops.record.MapValues._
import shapeless.syntax.singleton._
import shapelessplayjson.recordops.ZipByKey._

class Sandbox extends FunSuite with Matchers {
  val book =
    ('author ->> "Benjamin Pierce") ::
    ('title  ->> "Types and Programming Languages") ::
    ('id     ->>  262162091) ::
    ('price  ->>  44.11) ::
    HNil

  val schema =
    ('author ->> (__ \ "author").write[String]) ::
    ('title  ->> (__ \ "title").write[String]) ::
    ('id     ->>  (__ \ "id").write[Int]) ::
    ('price  ->>  (__ \ "price").write[Double]) ::
    HNil

  val semifinished = zipByKey(schema, book)

  object Writer extends Poly1 {
    implicit def owrt[T, K]: Case.Aux[OWrites[T] :: T :: HNil, JsObject] =
      at[OWrites[T] :: T :: HNil](v => v.head.writes(v.tail.head))
    implicit def ofmt[T, K]: Case.Aux[OFormat[T] :: T :: HNil, JsObject] =
      at[OFormat[T] :: T :: HNil](v => v.head.writes(v.tail.head))
  }

  object JsObjectJoin extends Poly {
    implicit def caseJso = use((a : JsObject, b : JsObject) => a ++ b)
  }

  /*
  test("Writes test") {
    val result = semifinished.mapValues(Writer).values.foldLeft(Json.obj())(JsObjectJoin)

   result shouldBe Json.obj(
     "author" -> JsString("Benjamin Pierce"),
     "title" -> JsString("Types and Programming Languages"),
     "id" -> JsNumber(262162091),
     "price" -> JsNumber(44.11)
   )
  }*/
}
