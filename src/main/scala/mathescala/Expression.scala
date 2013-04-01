package mathescala

import scala.math.Numeric

trait Expression {
	val head: Expression
	val args: Seq[Expression]
}

trait SymbolicExpression extends Expression {
	val symbol: Symbol
}

trait NumericExpression extends Expression {
	type numericType
	val value: numericType
}

object SymbolSymbol extends SymbolicExpression {
	val head: Expression = this
	val args: Seq[Expression] = Nil
	val symbol: Symbol = 'Symbol
	override def toString = symbol.toString
}

class SymbolExpression private (val symbol: Symbol) extends Expression {
	val head: Expression = SymbolSymbol
	val args: Seq[Expression] = Nil
	override def toString = symbol.toString
}

object SymbolExpression {
	def apply(symbol: Symbol) = if (symbol == SymbolSymbol.symbol) SymbolSymbol else new SymbolExpression(symbol)
}


class NumberExpression[T: Numeric] private (val v: T) extends NumericExpression {
	val head: Expression = SymbolExpression('Numeric)
	val args: Seq[Expression] = Nil
	type numericType = T
	val value: numericType = v
	override def toString = value.toString
}

object NumberExpression {
	def apply[T: Numeric](value: T) = new NumberExpression(value)
}

case class MathematicaExpression(head: Expression, args: Expression*) extends Expression {
	
}
