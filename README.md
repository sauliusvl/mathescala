# MatheScala [![Build Status](https://travis-ci.org/sauliusvl/mathescala.png)](https://travis-ci.org/sauliusvl/mathescala)

MatheScala is a scala DSL that lets you write [Mathematica](http://en.wikipedia.org/wiki/Mathematica) like code and perform symbolic computations in scala. The library was conceived as a proof of concept, hence it probably shouldn't be used in real life (not that anyone would want to do that). 

## Introduction

If you know Mathematica then the following should ring a bell:

```scala    
scala> import mathescala._
scala> import mathescala.implicits._

scala> ('$ ^ 2).& /@ 'Range(1, 5)
res0: mathescala.Expression = 'List(1, 4, 9, 16, 25)

scala> 'Plus @@ res0
res1: mathescala.Expression = 55
 
scala> 'fact(5) /:: Seq('fact('x_) ~> 'x * 'fact('x - 1), 'fact(1) ~> 1)
res2: mathescala.Expression = 120
```

Most of Mathematica's syntax is (rather *will be*) implemented with almost identical semantics, including pattern matching, pure functions, basic arithmetic evaluation and more. Of course due to to scala naming rules many operators have different names.

## Syntax

### Expressions

### Patterns and Replacements

### Pure Functions

## Examples