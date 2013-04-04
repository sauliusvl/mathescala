package mathescala

import mathescala._
import mathescala.implicits._

import org.scalatest.FunSuite

class PatternMatching extends FunSuite {

  test("Pattern detection works") {
    assert('Blank.isPattern)
    assert('Blank('Integer).isPattern)
    assert('f('Blank).isPattern)
    assert('List('_, 1).isPattern)
    assert('__.isPattern)
    assert('___.isPattern)
    assert('f('a_Integer).isPattern)
    assert('f('g('h('__))).isPattern)
    assert('_Integer.isPattern)
    assert('Plus(1, '_, '_).isPattern)
    assert(!'Plus('a, 'b, 'c).isPattern)
    assert(!'a.isPattern)
    assert(!1.isPattern)
  }

  test("Unnamed patterns match") {
    assert('f.matchAgainst('_).isMatch)
    assert(1.matchAgainst('_).isMatch)
    assert('f('x('y)).matchAgainst('_).isMatch)
    assert('f.matchAgainst('_f).isMatch)
    assert('f('x).matchAgainst('_f).isMatch)
    assert(!'f('x).matchAgainst('_g).isMatch)
  }

  test("Named patterns match") {
    val x1 = 'f.matchAgainst('a_); assert(x1.isMatch && x1.matches('a) == 'f.toExpression)
    val x2 = 'g.matchAgainst('b_g); assert(x2.isMatch && x2.matches('b) == 'g.toExpression)
    val x3 = 'g('x).matchAgainst('b_g); assert(x3.isMatch && x3.matches('b) == 'g('x))
  }

}
