package mathescala

import mathescala._
import mathescala.implicits._

import org.scalatest.FunSuite

class ExpressionEquality extends FunSuite {

  test("Basic equality") {
    val x: Expression = 'x
    val xx: Expression = 'x
    val y: Expression = 'y

    assert(x === x)
    assert(x === xx)
    assert(y(x) === y(x))
    assert('f('x, 'y) === 'f('x, 'y))
    assert('f(x) === 'f(xx))
    assert('Plus('Times(2, 'x), 2) === 'Plus('Times(2, 'x), 2))
    assert(x != y)
  }

  test("Head of Symbol is Symbol") {
    val x: Expression = 'x
    val symbol: Expression = 'Symbol

    assert(x.head === symbol)
    assert(symbol.head === symbol)
    assert(x.head.head === x.head)
    assert(x.head.head.head === symbol)
  }

  test("Expressions have correct heads") {
    assert('x.head === 'Symbol.toExpression)
    assert(1.head === 'Integer.toExpression)
    assert(2.12f.head === 'Real.toExpression)
    assert('Plus('x, 2).head === 'Plus.toExpression)
  }

}
