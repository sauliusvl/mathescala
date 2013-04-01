package mathescala

import mathescala._
import mathescala.implicits._

import org.scalatest.FunSuite

class ExpressionEquality extends FunSuite {

  test("Head of Symbol is Symbol") {
    val symbol: Expression = 'Symbol
    val other: Expression = 'x

    assert(symbol.head == symbol)
    assert(other != other.head)
    assert(other.head == symbol)
  }



}
