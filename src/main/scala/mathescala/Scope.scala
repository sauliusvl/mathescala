package mathescala

import scala.collection.mutable

trait Scope {
  val attributes: mutable.Map[Symbol, mutable.Set[Symbol]]
  val assignments: mutable.Map[Expression, Expression]
}
