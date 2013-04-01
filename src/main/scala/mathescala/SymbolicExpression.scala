package mathescala

import mathescala.implicits._

trait SymbolicExpression extends Expression {
  val symbol: Symbol

  override def canEqual(other: Any): Boolean = other.isInstanceOf[SymbolicExpression]
  override def equals(other: Any): Boolean = other match {
    case that: SymbolicExpression => this.symbol == that.symbol
    case _ => false
  }
  override def hashCode: Int = symbol.hashCode
  override def toString = symbol.name.toString
}

object SymbolicExpression {

  // Depending on the name this can construct either a simple symbolic expression,
  // a pattern or a slot for a pure function.
  def apply(name: Symbol): Expression = name.name match {
    case s if s.contains("#") => makeSlot(name)
    case s if s.contains("_") => makePattern(name)
    case s => new SymbolicExpression {
      val symbol: Symbol = name
      val args: Seq[Expression] = Nil
      val head: Expression = new SymbolicExpression {
        val symbol: Symbol = 'Symbol
        val args: Seq[Expression] = Nil
        val head: Expression = this // head of Symbol is Symbol
      }
    }
  }

  private def makeSlot(name: Symbol): Expression = {
    null
  }

  private def makePattern(name: Symbol): Expression = {
    null
  }
}
