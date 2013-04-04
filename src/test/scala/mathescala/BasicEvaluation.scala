package mathescala

import mathescala._
import mathescala.implicits._

import org.scalatest.FunSuite

class BasicEvaluation extends FunSuite {

  test("Addition works") {
    assert('Plus(2, 2).basicEval === 4.toExpression)
    assert('Plus(1, 2, 3, 4).basicEval === 10.toExpression)
    assert('Plus(1, 2.5).basicEval === 3.5.toExpression)
    //assert('f(1, 'Plus(2, 3)).basicEval === 'f(1, 5))

  }

}
