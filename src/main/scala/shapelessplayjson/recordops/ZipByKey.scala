package shapelessplayjson.recordops

import shapeless._
import shapeless.labelled.FieldType
import shapeless.ops.record._

// Original implementation by tksfz
// See: http://stackoverflow.com/questions/39757357/in-shapeless-given-two-records-how-do-i-require-that-both-records-have-the-sam

private [shapelessplayjson] trait ZipByKey[L <: HList, R <: HList] extends DepFn2[L, R] {
  type Out <: HList
}

private [shapelessplayjson] object ZipByKey {

  type Aux[L <: HList, R <: HList, O <: HList] = ZipByKey[L, R] { type Out = O }

  implicit def hnilZip[R <: HList] = new ZipByKey[HNil, R] { type Out = HNil; override def apply(l: HNil, r: R) = HNil }

  implicit def hlistZip[K, V, T <: HList, R <: HList, RV, Remainder <: HList, TO <: HList]
  (implicit
    remover: Remover.Aux[R, K, (RV, Remainder)],
    recurse: ZipByKey.Aux[T, Remainder, TO]
  ) = new ZipByKey[FieldType[K, V] :: T, R] {
    type Out = FieldType[K, (V, RV)] :: TO

    def apply(l: FieldType[K, V] :: T, r: R): Out = {
      val (rv, remainder) = remover.apply(r)
      val newValue = (l.head, rv)
      labelled.field[K](newValue) :: recurse.apply(l.tail, remainder)
    }
  }

  def zipByKey[G <: HList, R <: HList, O <: HList](g: G, r: R)(implicit zipByKey: ZipByKey.Aux[G, R, O]): O = {
    zipByKey.apply(g, r)
  }
}
