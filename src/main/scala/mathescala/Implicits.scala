package mathescala

import scala.language.implicitConversions
import scala.math.Numeric

class RichExpression(val e: Expression) {
  def toExpression: Expression = e
}

object implicits {
  implicit def fromSymbol(s: Symbol): Expression = SymbolicExpression(s)
  implicit def fromNumeric[T: Numeric](num: T): NumericExpression = NumericExpression(num)
  implicit def toRichExpression(s: Symbol): RichExpression = new RichExpression(s)
  implicit def toRichExpression[T: Numeric](num: T): RichExpression = new RichExpression(num)
}

