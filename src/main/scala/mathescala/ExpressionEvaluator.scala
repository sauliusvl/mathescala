package mathescala

import mathescala.implicits._

trait ExpressionEvaluator { self: Expression =>

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
    val ehead = head.basicEval
    val eargs = args.map(_.basicEval)

    this
    //scope.assignments


  }

  private def evalSet = {
    this
  }

  private def evalSetDelayed = {

  }

}
