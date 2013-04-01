package mathescala

import math.Numeric

import mathescala.implicits._

trait NumericExpression extends Expression {
  type numericType
  val value: numericType

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
  def apply[T: Numeric](num: T) = new NumericExpression {
    type numericType = T
    val value: this.type#numericType = num
    val args: Seq[Expression] = Nil
    val head: Expression = num match {
      case n: BigDecimal => 'Integer
      case n: Long => 'Integer
      case n: Int => 'Integer
      case n: Short => 'Integer
      case n: Byte => 'Integer
      case n: Char => 'Integer
      case n: Float => 'Real
      case n: Double => 'Real
      case n => throw new Exception("Unknown numeric type '" + n.getClass.getName + "'")
    }
  }
}
