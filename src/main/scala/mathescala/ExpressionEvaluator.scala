package mathescala

import mathescala.implicits._

trait ExpressionEvaluator { self: Expression =>

  // If the expression is flat, flatten it.
  def flatten(implicit scope: Scope): Expression = {
    head match {
      case hd if scope.isAttributeSet(hd, 'Flat) => {
        val flatArgs = args.map(_.flatten).flatMap(a => if (a.head == hd) a.args else Seq(a))
        head(flatArgs: _*)
      }
      case _ => this
    }
  }

  // If the expression is orderless, bring numerical factors to the front and sort other arguments alphabetically.
  def order(implicit scope: Scope): Expression = {
    head match {
      case hd if scope.isAttributeSet(hd, 'Orderless) => {
        head(args.map(_.order).sortBy(_ match {
          case n: NumericExpression => "0"
          case s: SymbolicExpression => "1" + s.symbol.toString()
          case e: Expression => "2" + e.head.toString()
        }): _*)
      }
      case _ => this
    }
  }

  def eval(implicit scope: Scope): Expression = {
//    def assuming(cond: => Boolean)(code: => Expression): Expression =
//      if (!cond) throw new SyntaxError("Syntax error") else code
//
//    val phead = head.basicEval
//    val pargs = args.map(_.basicEval)
//
//    phead match {
//      case s if s == 'Set.toExpression =>
//        assuming(pargs.length == 2) {
//          val rhs = pargs(1).eval
//          scope.assignments += pargs(0) -> rhs
//          rhs
//        }
//      case s if s == 'SetDelayed.toExpression =>
//        assuming(pargs.length == 2) {
//          scope.assignments += pargs(0) -> pargs(1)
//          'Null.toExpression
//        }
//      case _ => this
//    }

    // Do basic evaluation, take care of flattening, one identity and orderless
    //val ehead = head.basicEval
    //val eargs = args.map(_.basicEval)

    this
    //scope.assignments


  }

  private def evalSet = {
    this
  }

  private def evalSetDelayed = {

  }

}
