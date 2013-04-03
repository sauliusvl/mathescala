package mathescala

import scala.language.implicitConversions

class RichExpression(val e: Expression) {
  def toExpression: Expression = e
}

object implicits {
  implicit def fromSymbol(s: Symbol): Expression = SymbolicExpression(s)
  implicit def fromInt(num: Int): Expression = IntegerExpression(num)
  implicit def fromDouble(num: Double): Expression = RealExpression(num)
  implicit def toRichSymbol(s: Symbol): RichExpression = new RichExpression(s)
  implicit def toRichInteger(num: Int): RichExpression = new RichExpression(num)
  implicit def toRichReal(num: Double): RichExpression = new RichExpression(num)
}

