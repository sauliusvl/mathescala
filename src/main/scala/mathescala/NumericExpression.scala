package mathescala

import mathescala.implicits._

trait NumericExpression extends Expression {
  type numericType
  val value: numericType
  val args: Seq[Expression] = Nil

  override def eval(implicit scope: Scope): Expression = this
  override def arithmeticEval: Expression = this

  override def canEqual(other: Any): Boolean = other.isInstanceOf[NumericExpression]
  override def equals(other: Any): Boolean = other match {
    case that: NumericExpression => this.value == that.value
    case _ => false
  }
  override def hashCode: Int = value.hashCode
  override def toString = value match {
    case n: Char => n.toInt.toString
    case n => n.toString
  }
}

object NumericExpression {
  val zero: NumericExpression = IntegerExpression(0)
  val one:NumericExpression = IntegerExpression(1)

  def times(lhs: NumericExpression, rhs: NumericExpression): NumericExpression = (lhs, rhs) match {
    case (l: IntegerExpression, r: IntegerExpression) => IntegerExpression(l.value * r.value)
    case (l: IntegerExpression, r: RealExpression) => RealExpression(l.value * r.value)
    case (l: RealExpression, r: IntegerExpression) => RealExpression(l.value * r.value)
    case (l: RealExpression, r: RealExpression) => RealExpression(l.value * r.value)
  }
  def plus(lhs: NumericExpression, rhs: NumericExpression): NumericExpression = (lhs, rhs) match {
    case (l: IntegerExpression, r: IntegerExpression) => IntegerExpression(l.value + r.value)
    case (l: IntegerExpression, r: RealExpression) => RealExpression(l.value + r.value)
    case (l: RealExpression, r: IntegerExpression) => RealExpression(l.value + r.value)
    case (l: RealExpression, r: RealExpression) => RealExpression(l.value + r.value)
  }
}

trait IntegerExpression extends NumericExpression {
  type numericType = Int
}

trait RealExpression extends NumericExpression {
  type numericType = Double
}

object IntegerExpression {
  def apply(num: Int) = new IntegerExpression {
    val value: Int = num
    val head: Expression = 'Integer
  }
}

object RealExpression {
  def apply(num: Double) = new RealExpression {
    val value: Double = num
    val head: Expression = 'Real
  }
}
