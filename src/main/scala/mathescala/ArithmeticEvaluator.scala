package mathescala

import mathescala.implicits._

trait ArithmeticEvaluator { self: Expression =>

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

}
