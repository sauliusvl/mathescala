package mathescala

import mathescala.implicits._

trait ArithmeticEvaluator { self: Expression with ExpressionEvaluator =>

  def arithmeticEval: Expression =
    this.head(args.map(_.arithmeticEval): _*)
      .flatten
      .evaluateNumerics
      .groupCommonTerms
      .removeIdentities
      .simplifyArithmetic
      .order

  // Do numeric evaluation.
  private def evaluateNumerics: Expression = {
    val (numbers, others) = args.partition {
      case e: NumericExpression => true
      case _ => false
    }
    val nums = numbers.map(_.asInstanceOf[NumericExpression])

    val calculated = if (head == 'Plus.toExpression) {
      'Plus(nums.foldLeft(NumericExpression.zero)((n1, n2) => NumericExpression.plus(n1, n2)) +: others: _*)
    } else if (head == 'Times.toExpression) {
      if (nums.contains(0.toExpression))
        0.toExpression
      else
        'Times(nums.foldLeft(NumericExpression.one)((n1, n2) => NumericExpression.times(n1, n2)) +: others: _*)
    } else {
      head(numbers ++ others: _*)
    }
    calculated.removeIdentities
  }

  // Remove zeros from sums and ones from multiplication.
  private def removeIdentities: Expression = {
    val removed = if (head == 'Plus.toExpression) {
      val r = 'Plus(args.filter(a => a != 0.toExpression): _*)
      if (r.args.size == 0) 0.toExpression else r
    } else if (head == 'Times.toExpression) {
      val r = 'Times(args.filter(a => a != 1.toExpression): _*)
      if (r.args.size == 0) 1.toExpression else r
    } else
      this

    // Eliminate single argument plus and times, e.g. Plus[1] becomes 1
    if ((removed.head == 'Plus.toExpression || removed.head == 'Times.toExpression) && (removed.args.size == 1))
      removed.args(0)
    else
      removed
  }

  // Group common terms under plus and times.
  private def groupCommonTerms: Expression = {
    val grouped = if (head == 'Plus.toExpression) {
      'Plus(args.groupBy(g => g).map(g => if (g._2.length > 1) 'Times(g._2.length, g._1) else g._1).toSeq: _*)
    } else if (head == 'Times.toExpression) {
      'Times(args.groupBy(g => g).map(g => if (g._2.length > 1) 'Power(g._1, g._2.length) else g._1).toSeq: _*)
    } else
      this
    grouped.flatten
  }

  // Do rudimentary simplifications c1 a + c2 a -> (c1 + c2) a, where c1 and c2 are constants.
  private def simplifyArithmetic: Expression = {
    if (head == 'Plus.toExpression) {
      val products = order.args.collect {
        case expr: Expression if expr.head == 'Times.toExpression && expr.args(0).isInstanceOf[NumericExpression] => expr
        case expr: Expression if expr.head == 'Times.toExpression => 'Times((1.toExpression +: expr.args): _*)
        case expr: Expression => 'Times(1.toExpression, expr)
      }
      val productGroups = products
        .groupBy(prod => prod.args.drop(1))
        .map(gr => 'Times(('Plus(gr._2.map(_.args(0)): _*).arithmeticEval +: gr._1): _*).removeIdentities).toSeq
      'Plus(productGroups: _*).removeIdentities.evaluateNumerics
    } else
      this
  }

}
