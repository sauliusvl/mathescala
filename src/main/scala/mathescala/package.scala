import collection.mutable

package object mathescala {
  implicit object DefaultScope extends Scope {
    val attributes: mutable.Map[Symbol, mutable.Set[Symbol]] = mutable.Map(
      'Plus -> mutable.Set('Orderless, 'Flat),
      'Times -> mutable.Set('Orderless, 'Flat)
    )
    val assignments: mutable.Map[Expression, Expression] = mutable.Map()
  }

  class ParseException(msg: String) extends Exception(msg)
}


