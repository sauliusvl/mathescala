package mathescala

import scala.language.implicitConversions
import scala.math.Numeric

object implicits {
  implicit def fromSymbol(s: Symbol): Expression = SymbolicExpression(s)
  implicit def fromNumeric[T: Numeric](num: T): NumericExpression = NumericExpression(num)
}

