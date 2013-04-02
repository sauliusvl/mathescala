# MatheScala [![Build Status](https://travis-ci.org/sauliusvl/mathescala.png)](https://travis-ci.org/sauliusvl/mathescala)

MatheScala is a simple scala DSL that lets you write [Mathematica](http://en.wikipedia.org/wiki/Mathematica) code and perform symbolic computations in scala. The library was conceived as a proof of concept, hence it probably shouldn't be used in real life (not that anyone would want to do that). 

## Introduction

If you know [Mathematica](http://en.wikipedia.org/wiki/Mathematica) then the following should ring a bell:

```scala    
import mathescala._
import mathescala.implicits._

val squares = ('# ^ 2).& /@ 'Range(1, 10)
val sum = 'Plus @@ squares
val fact = 'fact(5) /:: Seq('fact('x_) --> 'x * 'fact('x - 1), 'fact(1) --> 1)
```

Most of Mathematica's syntax is (rather *will be*) implemented with almost identical semantics, including pattern matching, pure functions, basic arithmetic evaluation and more. Of course, obviously due to to scala naming rules many operators have different names.

## Syntax

### Expressions

### Patterns and Replacements

### Pure Functions

## Examples