package mathescala

import mathescala._
import mathescala.implicits._

import org.scalatest.FunSuite

class OperatorShorthands extends FunSuite {

  test("Arithmetic operator shorthands expand correctly") {
    assert('x + 2 === 'Plus('x, 2))
    assert('x * 'f('y) === 'Times('x, 'f('y)))
    assert(('x ^ 'y) === 'Power('x, 'y))
    assert('x / 2 === 'Times('x, 'Power(2, -1)))
    assert('x - 'y === 'Plus('x, 'Times(-1, 'y)))
  }

  test("Application operator shorthands expand correctly") {
    assert('Times ~@ 'x === 'Times('x))
    assert('Plus @@ 'f('x) === 'Apply('Plus, 'f('x)))
    assert('x @@@ 'y === 'Apply('x, 'y, 'List(1)))
  }

  test("Assignment operator shorthands expand correctly") {
    assert(('x('y) := 'z) === 'SetDelayed('x('y), 'z))
    assert(('x ~= 'y) === 'Set('x, 'y))
  }

  test("Replacement operator shorthands expand correctly") {
    assert('a ~> 'b === 'Rule('a, 'b))
    assert('x :> 'f('x) === 'RuleDelayed('x, 'f('x)))
    assert('x(2) /: 'z === 'ReplaceAll('x(2), 'z))
    assert('z /:: 'List('x, 'y) === 'ReplaceRepeated('z, 'List('x, 'y)))
  }

  test("Misc operator shorthands expand correctly") {
    assert('f /@ 'List(1, 2) === 'Map('f, 'List(1, 2)))
    assert('x.& == 'Function('x))
    assert('x ? 'y === 'PatternTest('x, 'y))
  }

}
