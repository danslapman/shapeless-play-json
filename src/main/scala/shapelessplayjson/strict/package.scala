package shapelessplayjson

import play.api.libs.json._
import shapeless._
import shapeless.labelled._

package object strict {
  def strictWrites[K <: Symbol, H, T <: HList](schema: ::[FieldType[K, _ <: Writes[H]], _ <: Writes[T] with HList])(implicit
    witness: Witness.Aux[K]
  ): Writes[FieldType[K, H] :: T] = {
    (hl: FieldType[K, H] :: T) => {
      schema.tail.writes(hl.tail) match {
        case tjso: JsObject =>
          tjso + (witness.value.name -> schema.head.writes(hl.head))
        case _ =>
          throw new RuntimeException("tail serializer must return JsObject")
      }
    }
  }
}
