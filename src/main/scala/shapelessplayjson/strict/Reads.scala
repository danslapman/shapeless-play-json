package shapelessplayjson.strict

import play.api.libs.json.{JsResult, JsValue, Reads}
import shapeless.{HList, Poly1, Poly2}
import shapeless.labelled.{FieldType, field}

object SchemaConverter extends Poly1 {
  implicit def fieldReads[K, V] = at[FieldType[K, Reads[V]]] { r =>
    (r.reads _).andThen(_.map(v => field[K](v)))
  }
}

object ReadsComposer extends Poly2 {
  implicit def compose[L <: HList, K, V] = at[(JsValue) => JsResult[FieldType[K, V]], (JsValue) => JsResult[L]] { (fex, lex) =>
    (input: JsValue) => {
      for {
        field <- fex(input)
        tail <- lex(input)
      } yield field :: tail
    }
  }
}