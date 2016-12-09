package shapelessplayjson.selective

import org.scalatest.{FunSuite, Matchers}
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import shapeless._
import shapeless.ops.record.Values
import shapeless.ops.record.Keys
import shapeless.record._
import shapeless.syntax.singleton._
import ops.function._
import ops.hlist._
import Tupler._
import Values._
import ZipWithKeys._
import syntax.std.function._

class SandBox extends FunSuite with Matchers {
  type RItem = Record.`'name -> String, 'q -> Int`.T
  val item =
    ('name ->> "Yoba") ::
    ('q ->> 42) :: HNil

  case class Item(name: String, q: Int)

  val builder =
    (__ \ "name").format[String] ~
    (__ \ "q").format[Int]

  val cc = builder(Item.apply, unlift(Item.unapply))

  def unapplyRecord[B <: HList : Values] = (b: B) => b.values

  def applyRecord(v1: String, v2: Int): RItem = ('name ->> v1) :: ('q ->> v2) :: HNil

  def applyRecordHL[B <: HList : Values : Keys](implicit
    withKeys: ZipWithKeys[Keys[B]#Out, Values[B]#Out]
  ) = (v: Values[B]#Out) => v.zipWithKeys[Keys[B]#Out]

  //val applyRItem = applyRecordHL[RItem]

  val recordBuilder = builder(applyRecord, unapplyRecord[RItem].andThen(_.tupled))


  test("Dummy") {
    println(recordBuilder.writes(item))
  }
}
