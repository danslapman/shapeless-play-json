import play.api.libs.json._
import shapeless._

package object shapelessplayjson {
  implicit val hNilWrites: OWrites[HNil] =
    (_: HNil) => Json.obj()

  implicit val hNilReads: Reads[HNil] = {
    case JsObject(_) => JsSuccess(HNil)
    case _ => JsError("HNil must be represented as empty object")
  }
}
