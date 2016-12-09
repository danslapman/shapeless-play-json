package shapelessplayjson

import play.api.libs.json._
import shapeless._
import shapeless.labelled._

package object auto {
  implicit def recordWrites[K <: Symbol, H, T <: HList](implicit
    witness: Witness.Aux[K],
    hWrites: Lazy[Writes[H]],
    tWrites: Writes[T]
  ): Writes[FieldType[K, H] :: T] = {
    (hl: FieldType[K, H] :: T) => {
      tWrites.writes(hl.tail) match {
        case tjso: JsObject =>
          tjso + (witness.value.name -> hWrites.value.writes(hl.head))
        case _ =>
          throw new RuntimeException("tail serializer must return JsObject")
      }
    }
  }

  implicit def recordReads[K <: Symbol, H, T <: HList](implicit
    witness: Witness.Aux[K],
    hReads: Lazy[Reads[H]],
    tReads: Reads[T]
  ): Reads[FieldType[K, H] :: T] = (json: JsValue) => {
    val fieldName = witness.value.name
    json match {
      case jso: JsObject =>
        (json \ fieldName).toOption match {
          case Some(jsv) =>
            for {
              hv <- hReads.value.reads(jsv)
              tv <- tReads.reads(jso)
            } yield {
              field[K](hv) :: tv
            }
          case None =>
            JsError(__ \ fieldName, s"Field '$fieldName' not found")
        }
      case _ =>
        JsError(s"${json.getClass} can't be deserialized into record type")
    }
  }
}
