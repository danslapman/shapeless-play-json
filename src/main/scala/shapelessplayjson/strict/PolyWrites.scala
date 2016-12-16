package shapelessplayjson.strict

import play.api.libs.json.{JsObject, JsPath, Json, OWrites}
import shapeless.{HList, Poly1}
import shapeless.labelled.FieldType
import shapeless.ops.hlist.MapFolder

trait PolyWrites extends Poly1 {
  def write[K, V](ov: OWrites[V]) = at[FieldType[K, V]](ov.writes)

  implicit def writes[R <: HList](implicit mapFolder: MapFolder[R, JsObject, this.type]): OWrites[R] =
    (r: R) => mapFolder(r, Json.obj(), _ ++ _)
}
