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

  def basicEval: Expression = {
    if (head == 'Plus.toExpression || head == 'Times.toExpression) {
      val eargs = args.map(_.basicEval)
      val ints = eargs.collect { case e: IntegerExpression => e}
      val reals = eargs.collect { case e: RealExpression => e}
      val others = eargs.filter(a => !ints.contains(a) && !reals.contains(a))
      def agg[T](collection: Seq[T])(implicit num: Numeric[T]): T = {
        if (head == 'Plus.toExpression)
          collection.foldLeft(num.zero){num.plus(_, _)}
        else
          collection.foldLeft(num.one){num.times(_, _)}
      }
      val sum: Expression = if (reals.isEmpty)
          IntegerExpression(agg(ints.map(_.value)))
        else
          RealExpression(agg(ints.map(_.value)) + agg(reals.map(_.value)))
      val result = if (others.isEmpty) sum else head((sum +: others): _*)
      if (result.head == 'Plus.toExpression) {
        'Plus(result.args.groupBy(g => g).map(g => if (g._2.length > 1) 'Times(g._2.length, g._1) else g._1).toSeq: _*)
      } else if (result.head == 'Times.toExpression) {
        'Times(result.args.groupBy(g => g).map(g => if (g._2.length > 1) 'Power(g._1, g._2.length) else g._1).toSeq: _*)
      } else
        result
    } else this
  }

  def eval(implicit scope: Scope): Expression = {
    def assuming(cond: => Boolean)(code: => Expression): Expression =
      if (!cond) throw new SyntaxError("Syntax error") else code // in" + unevaluatedToString) else code

    head match {
      case s if s == 'Set.toExpression =>
        assuming(args.length == 2) {
          val rhs = args(1).eval
          scope.assignments += args(0) -> rhs
          rhs
        }
      case s if s == 'SetDelayed.toExpression =>
        assuming(args.length == 2) {
          scope.assignments += args(0) -> args(1)
          'Null.toExpression
        }
      case _ => this
    }
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

  //def unevaluatedToString: String = head.unevaluatedToString + "[" + args.map(_.unevaluatedToString).mkString(", ") + "]"

  override def toString = {
    val evaluated = eval
    evaluated.head.toString + "[" + evaluated.args.mkString(", ") + "]"
  }
}

