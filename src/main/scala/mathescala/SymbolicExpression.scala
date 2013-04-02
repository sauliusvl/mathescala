package mathescala

import scala.util.matching.Regex
import mathescala.implicits._

trait SymbolicExpression extends Expression {
  val symbol: Symbol

  override def canEqual(other: Any): Boolean = other.isInstanceOf[SymbolicExpression]
  override def equals(other: Any): Boolean = other match {
    case that: SymbolicExpression => this.symbol == that.symbol
    case _ => false
  }
  override def hashCode: Int = symbol.hashCode
  override def toString = symbol.name.toString
}

object SymbolicExpression {

  // Depending on the name this can construct either a simple symbolic expression,
  // a pattern or a slot for a pure function.
  def apply(name: Symbol): Expression = name.name match {
    case s if s.contains("$") => makeSlot(name)
    case s if s.contains("_") => makePattern(name)
    case s => new SymbolicExpression {
      val symbol: Symbol = name
      val args: Seq[Expression] = Nil
      val head: Expression = new SymbolicExpression {
        val symbol: Symbol = 'Symbol
        val args: Seq[Expression] = Nil
        val head: Expression = this // head of Symbol is Symbol
      }
    }
  }

  private def makeSlot(symbol: Symbol): Expression = {
    if (!symbol.name.startsWith("$"))
      throw new ParseException("Invalid symbol '" + symbol.name + "'")
    else {
      val num = symbol.name.substring(1)
      if (num.isEmpty)
        'Slot(1)
      else {
        try {
          'Slot(num.toInt)
        } catch {
          case _: NumberFormatException => throw new ParseException("Invalid symbol '" + symbol.name + "'")
        }
      }
    }
  }

  private def makePattern(symbol: Symbol): Expression = {
    val Pattern = """([a-zA-Z]*)(_{1,3})([a-zA-Z]*)""".r
    try {
      val Pattern(name, seq, head) = symbol.name
      val blank: Expression = seq.length match {
        case 1 => 'Blank
        case 2 => 'BlankSequence
        case 3 => 'BlankNullSequence
        case _ => throw new Exception("Internal Error")
      }
      val blankFilled = if (head.length > 0) blank(Symbol(head)) else blank
      if (name.length > 0) 'Pattern(Symbol(name), blankFilled) else blankFilled
    } catch {
      case _: MatchError => throw new ParseException("Invalid symbol '" + symbol.name + "'")
    }
  }
}
