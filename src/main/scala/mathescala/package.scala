package mathescala

import scala.language.implicitConversions
import scala.math.Numeric

object implicits {
	implicit def fromSymbol(s: Symbol) = SymbolExpression(s)
	implicit def fromNumeric[T: Numeric](num: T) = NumberExpression(num)
}