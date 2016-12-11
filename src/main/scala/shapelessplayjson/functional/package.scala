package shapelessplayjson

import play.api.libs.json._
import shapeless._
import shapeless.labelled._
import shapeless.record._
import shapeless.ops.record.Values

package object functional {
  def unapplyRecord[B <: HList : Values] = (b: B) => b.values
}
