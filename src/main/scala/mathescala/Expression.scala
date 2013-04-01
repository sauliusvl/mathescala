package mathescala

import mathescala.implicits._

trait Expression {
	val head: Expression
	val args: Seq[Expression]

  // Syntactic sugar for constructing arithmetic expressions.

  def +(rhs: Expression): Expression = 'Plus(this, rhs)
  def *(rhs: Expression): Expression = 'Times(this, rhs)
  def /(rhs: Expression): Expression = 'Times(this, 'Power(rhs, -1))
  def -(rhs: Expression): Expression = 'Plus(this, 'Times(-1, rhs))
  def ^(rhs: Expression): Expression = 'Power(this, rhs)

  def & = 'Function(this)

  // Applying arguments to an expression constructs a new expression with this one
  // as the head and the passed arguments as the arguments.
  def apply(arguments: Expression*) = {
    val hd = this
    new Expression {
      val args: Seq[Expression] = arguments
      val head: Expression = hd
    }
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Expression]
  override def equals(other: Any): Boolean = other match {
    case that: Expression => (that canEqual this) && (this.head == that.head) && (this.args == that.args)
    case _ => false
  }
  override def hashCode: Int = (41 * (41 + head.hashCode)) + args.hashCode()

  override def toString = head.toString + "[" + args.mkString(", ") + "]"
}

