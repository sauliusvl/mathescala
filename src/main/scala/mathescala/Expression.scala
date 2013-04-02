package mathescala

import mathescala.implicits._

trait Expression {
  val head: Expression
  val args: Seq[Expression]

  // Arithmetic expressions
  def +(rhs: Expression): Expression = 'Plus(this, rhs)
  def *(rhs: Expression): Expression = 'Times(this, rhs)
  def /(rhs: Expression): Expression = 'Times(this, 'Power(rhs, -1))
  def -(rhs: Expression): Expression = 'Plus(this, 'Times(-1, rhs))
  def ^(rhs: Expression): Expression = 'Power(this, rhs)

  // Application
  def ~@(rhs: Expression): Expression = this(rhs)
  def @@(rhs: Expression): Expression = 'Apply(this, rhs)
  def @@@(rhs: Expression): Expression = 'Apply(this, rhs, 'List(1))

  // Assignment
  def :=(rhs: Expression): Expression = 'SetDelayed(this, rhs)
  def ~=(rhs: Expression): Expression = 'Set(this, rhs)

  // Rules and replacement
  def ~>(rhs: Expression): Expression = 'Rule(this, rhs)
  def :>(rhs: Expression): Expression = 'RuleDelayed(this, rhs)
  def /:(rhs: Expression): Expression = 'ReplaceAll(rhs, this)
  def /::(rhs: Expression): Expression = 'ReplaceRepeated(rhs, this)

  // Misc shorthands
  def & = 'Function(this)
  def /@(rhs: Expression): Expression = 'Map(this, rhs)
  def ?(rhs: Expression): Expression = 'PatternTest(this, rhs)

  def eval(implicit scope: Scope): Expression = {
    this
  }

  // Applying arguments to an expression constructs a new expression with this one
  // as the head and the passed arguments as the arguments.
  def apply(arguments: Expression*) = {
    val self = this
    new Expression {
      val args: Seq[Expression] = arguments
      val head: Expression = self
    }
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Expression]

  override def equals(other: Any): Boolean = other match {
    case that: Expression => (that canEqual this) && (this.head == that.head) && (this.args == that.args)
    case _ => false
  }

  override def hashCode: Int = (41 * (41 + head.hashCode)) + args.hashCode()

  override def toString = {
    val evaluated = eval
    evaluated.head.toString + "[" + evaluated.args.mkString(", ") + "]"
  }
}

