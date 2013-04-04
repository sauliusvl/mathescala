package mathescala

import mathescala.implicits._

trait NumericExpression extends Expression {
  type numericType
  val value: numericType
  val args: Seq[Expression] = Nil

  override def eval(implicit scope: Scope): Expression = this

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
