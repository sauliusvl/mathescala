package mathescala

import mathescala._
import mathescala.implicits._

import org.scalatest.FunSuite

class BasicOperations extends FunSuite {

  test("Replacing the head") {
    assert('Plus @@ 'Times('a, 'b) === 'Plus('a, 'b))
  }

}
