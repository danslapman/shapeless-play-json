package shapelessplayjson.strict

import play.api.libs.json._
import shapeless._
import shapeless.labelled._
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

  object Reader extends Poly1 {
    implicit def fr[K, V] = at[FieldType[K, Reads[V]]] { r =>
      (r.reads _).andThen(_.map(v => field[K](v)))
    }
  }

  object Folder extends Poly2 {
    implicit def compose[L <: HList, K, V] = at[(JsValue) => JsResult[FieldType[K, V]], (JsValue) => JsResult[L]] { (fex, lex) =>
      (input: JsValue) => {
        for {
          field <- fex(input)
          tail <- lex(input)
        } yield field :: tail
      }
    }
  }

  val reader = schema.map(Reader).foldRight(hNilReads.reads _)(Folder)

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
