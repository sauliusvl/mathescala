package mathescala

import scala.collection.mutable

trait Scope {
  val attributes: mutable.Map[Symbol, mutable.Set[Symbol]]
  val assignments: mutable.Map[Expression, Expression]

  def isAttributeSet(e: Expression, attr: Symbol): Boolean = e match {
    case hd: SymbolicExpression => attributes.getOrElse(hd.symbol, Set[Symbol]()).contains(attr)
    case _ => false
  }
}
