package shapelessplayjson.strict

import play.api.libs.json._
import shapeless.labelled.FieldType
import shapeless.ops.hlist.MapFolder
import shapeless.{HList, Poly1}

trait PolyWrites extends Poly1 {
  def write[K, V](path: JsPath)(implicit wrt: Writes[V]) = at[FieldType[K, V]](path.write(wrt).writes)

  implicit def writes[R <: HList](implicit mapFolder: MapFolder[R, JsObject, this.type]): OWrites[R] =
    (r: R) => mapFolder(r, Json.obj(), _ ++ _)
}
