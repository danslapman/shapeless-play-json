package shapelessplayjson.strict

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import shapeless._
import shapeless.labelled._
import shapeless.syntax.singleton._
import shapelessplayjson._
import shapeless.ops.hlist._
import MapFolder._

import org.scalatest.{FunSuite, Matchers}

trait PolyWrites extends Poly1 {
  def write[K, V](ow: OWrites[V]) = at[FieldType[K, V]](ow.writes)
}

class RecordSerializationTests extends FunSuite with Matchers {
  object PolyFormat extends PolyWrites {
    implicit def caseId = write[Witness.`'id`.T, Int]((__ \ "id").write[Int])
    implicit def caseName = write[Witness.`'name`.T, String]((__ \ "name").write[String])
  }

  private val item =
    ('id ->> 42) ::
    ('name ->> "Yoba") ::
    HNil

  def polyWrites[R <: HList, F <: Poly](polyFormat: F)(implicit mapFolder: MapFolder[R, JsObject, F]): OWrites[R] =
    (o: R) => mapFolder(o, Json.obj(), _ ++ _)

  test("Serialize record") {
    implicit def wr[R <: HList](implicit mapFolder: MapFolder[R, JsObject, PolyFormat.type]) =
      polyWrites[R, PolyFormat.type](PolyFormat)

    val result = Json.toJson(item)
    result shouldBe Json.obj("id" -> 42, "name" -> "Yoba")
  }
}
