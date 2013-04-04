package mathescala

import mathescala.implicits._

class PatternMatch(val isMatch: Boolean, val matches: Map[Symbol, Expression])

trait PatternMatcher { self: Expression =>

  def isPattern: Boolean = {
    this match {
      case e: SymbolicExpression => List('Blank, 'BlankSequence, 'BlankNullSequence).contains(e.symbol)
      case e: NumericExpression => false
      case e => head.isPattern || args.exists(a => a.isPattern)
    }
  }

  private lazy val emptyMatch = Map[Symbol, Expression]()

  def matchesAgainstBlank(blank: Expression): Boolean = {
    if (blank.args.length > 0)
      if (this.head == 'Symbol.toExpression)
        this == blank.args(0)
      else
        this.head == blank.args(0)
    else true
  }

  def matchAgainst(pattern: Expression): PatternMatch = {
    if (pattern == 'Blank.toExpression || pattern.head == 'Blank.toExpression) {
      new PatternMatch(matchesAgainstBlank(pattern), emptyMatch)
    } else if (pattern.head == 'Pattern.toExpression) {
      val name = pattern.args(0) match { case e: SymbolicExpression => e.symbol }
      val blank = pattern.args(1)
      new PatternMatch(matchesAgainstBlank(blank), Map(name -> this))
    } else {
      new PatternMatch(false, emptyMatch)
    }
  }
}
