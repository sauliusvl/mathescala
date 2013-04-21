package mathescala

import mathescala._
import mathescala.implicits._

import org.scalatest.FunSuite

class ArithmeticEvaluation extends FunSuite {

  test("Arithmetic evaluation produces correct heads") {
    assert('Plus(2, 2).arithmeticEval.head === 'Integer.toExpression)
    assert('Plus(2, 2.1).arithmeticEval.head === 'Real.toExpression)
    assert('Plus(2.3, 2.1).arithmeticEval.head === 'Real.toExpression)
    assert('Times(1.1, 2).arithmeticEval.head === 'Real.toExpression)
    assert('Times(2, 2).arithmeticEval.head === 'Integer.toExpression)
  }

  test("Addition works") {
    assert('Plus(2, 2).arithmeticEval === 4.toExpression)
    assert('Plus(1, 2, 3, 4).arithmeticEval === 10.toExpression)
    assert('Plus(1, 2.5).arithmeticEval === 3.5.toExpression)
    assert('f(1, 'Plus(2, 3)).arithmeticEval === 'f(1, 5))
    assert('f(1, 'Plus(2, 2), 3).arithmeticEval === 'f(1, 4, 3))
    assert('Plus('Plus(2, 3), 6).arithmeticEval === 11.toExpression)
    assert('Plus('a, 'b).arithmeticEval === 'Plus('a, 'b))
  }

  test("Multiplication works") {
    assert('Times(0, 1, 'a, 'b).arithmeticEval === 0.toExpression)
    assert('Times(2, 2).arithmeticEval === 4.toExpression)
    assert('Times(1, 2, 3, 4).arithmeticEval === 24.toExpression)
    assert('Times(1, 2.5).arithmeticEval === 2.5.toExpression)
    assert('f(1, 'Times(2, 3)).arithmeticEval === 'f(1, 6))
    assert('f(1, 'Times(2, 5), 3).arithmeticEval === 'f(1, 10, 3))
    assert('Times('Times('Times(2, 3), 2), 2).arithmeticEval === 24.toExpression)
    assert('Times('a, 'b).arithmeticEval === 'Times('a, 'b))
  }

  test("Single argument Plus and Times are simplified") {
    assert('Plus(2).arithmeticEval === 2.toExpression)
    assert('Times(1).arithmeticEval === 1.toExpression)
  }

  test("Identities are removed from Plus and Times") {
    assert('Plus('a, 0, 0, 0, 0).arithmeticEval === 'a.toExpression)
    assert('Times('b, 1, 1, 'c).arithmeticEval === 'Times('b, 'c))
    assert('Times(0, 'SuperComplicated('f('x, 'y), 2)).arithmeticEval === 0.toExpression)
  }

  test("Common terms are collected") {
    assert('Plus('a, 'a, 'a).arithmeticEval === 'Times(3, 'a))
    assert('Times('a, 'a, 'a).arithmeticEval === 'Power('a, 3))
    assert('Times('Plus('a, 'b), 'Plus('a, 'b)).arithmeticEval === 'Power('Plus('a, 'b), 2))
  }

  test("Plus and Times are expanded") {
    assert('Plus('a, 'Times('b, 'b), 'Plus(2, 3, 4), 'c).arithmeticEval === 'Plus(9, 'a, 'c, 'Power('b, 2)))
    assert('Plus('Times('Plus(2, 3), 2), 4).arithmeticEval === 14.toExpression)
    assert('Times('Times(2, 'a), 3).arithmeticEval === 'Times(6, 'a))
  }

  test("Rudimentary arithmetic simplification works") {
    assert('Plus('Times(2, 'a), 'Times(3, 'a)).arithmeticEval === 'Times(5, 'a))
    assert('Plus('Times(2, 'x), 'Times(-2, 'x)).arithmeticEval === 0.toExpression)
    assert('Plus('a, 'Times(-2, 'a), 'a).arithmeticEval === 0.toExpression)
    assert('Plus('Times('f('x), 'g('y)), 'Times('g('y), 'f('x)), 'Times(-2, 'f('x), 'g('y))).arithmeticEval === 0.toExpression)
    assert('f('Plus('a, 'Times(3, 'a))).arithmeticEval === 'f('Times(4, 'a)))
  }
}
