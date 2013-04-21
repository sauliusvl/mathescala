package mathescala

import mathescala._
import mathescala.implicits._

import org.scalatest.FunSuite

class AttributeEvaluation extends FunSuite {

  test("Flattening works") {
    assert('Plus('Plus('a, 'b), 'c).flatten === 'Plus('a, 'b, 'c))
    assert('Times('Times('Times('a), 'b), 'c).flatten === 'Times('a, 'b, 'c))
    assert('f('f('f('x))).flatten === 'f('f('f('x))))
  }

}
