package mathescala

import mathescala._
import mathescala.implicits._

import org.scalatest.FunSuite

class SpecialSymbols extends FunSuite {

  test("Function slots are created correctly") {
    assert('$1.toExpression === 'Slot(1))
    assert('$.toExpression === 'Slot(1))
    assert('$12.toExpression === 'Slot(12))
  }

  test("Bad function slot patterns fail to parse") {
    intercept[ParseException] { '$a.toExpression }
    intercept[ParseException] { 'a$.toExpression }
    intercept[ParseException] { '$1b.toExpression }
  }

  test("Pattern symbols parse correctly") {
    assert('_.toExpression === 'Blank.toExpression)
    assert('x_.toExpression === 'Pattern('x, 'Blank))
    assert('x__.toExpression == 'Pattern('x, 'BlankSequence))
    assert('_Integer.toExpression === 'Blank('Integer))
    assert('x_h.toExpression === 'Pattern('x, 'Blank('h)))
    assert('x__h.toExpression === 'Pattern('x, 'BlankSequence('h)))
    assert('x___.toExpression === 'Pattern('x, 'BlankNullSequence))
    assert('x___h.toExpression === 'Pattern('x, 'BlankNullSequence('h)))
  }

  test("Bad pattern symbols fail to parse") {
    intercept[ParseException] { '$_b_.toExpression }
    intercept[ParseException] { '$_1.toExpression }
    intercept[ParseException] { '$a__b_.toExpression }
    intercept[ParseException] { '$c___c__.toExpression }
  }
}
