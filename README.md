# kotlin-for-java-dev

<p>Course created by Jetbrains.</p>
<p>Available at Coursera: https://www.coursera.org/learn/kotlin-for-java-developers</p>

## Week 2

### Mastermind game</h3>

[Mastermind](https://en.wikipedia.org/wiki/Mastermind_(board_game)) is a board game for two players. 
The first player invents a code and the second player tries to guess this code.
A code is made up 4 coloured pins and their position.
There are 6 colours to choose from and the same colour can be repeated multiple times. 

Examples of different codes:

* Red Green Blue Yellow
* Red Green Yellow Blue
* Black White Red Orange
* Red Red Blue White

(note that while the first two codes use the same colours, they are different as Blue and Yellow occupy different positions)

The game play is as follows:

The second player (the one that is guessing) sets out a series of pins in order to guess the code.
The first player (that defined the code) then provides some feedback to the player in light of how close they are to the correct combination. 

The feedback is as follows:

- Number of pins that are both the right colour and position
- Number of pins that are correct in colour but in the wrong position

Note that the rest pins in the code will pins that are neither correct in colour or position.

Your task is to evaluate a guess made by player two of the code set by player one.
For the sake of simplicity, we use  uppercase letters from A to F instead of colours.

You can test your solution and play as a second player using `playMastermind`.

#### Different Letters

##### Example 1

Secret `ABCD` and guess `ABCD` must be evaluated to: `rightPosition = 4, wrongPosition = 0`.
All letters are guessed correctly in respect to their positions.

##### Example 2

Secret `ABCD` and guess `CDBA` must be evaluated to: `rightPosition = 0, wrongPosition = 4`.
All letters are guessed correctly, but none has the right position.

##### Example 3

Secret `ABCD` and guess `ABDC` must be evaluated to: `rightPosition = 2, wrongPosition = 2`.
`A` and `B` letters and their positions are guessed correctly.
`C` and `D` letters are guessed correctly, but their positions are wrong.

At first, you can implement this easier task, when all the letters are different,
and only after that start with the next part, when letters can be repeated.
Run `MastermindTestDifferentLetters` to make sure you've implemented this part correctly.  

#### Repeated Letters

##### Example 4

Secret `AABC` and guess `ADFE` must be evaluated to: `rightPosition = 1, wrongPosition = 0`.
`A` at the first position is guessed correctly with its position.
If we remove the first `A` from consideration (comparing the remaining `ABC` and
`DFE` only), that will give us no more common letters or positions.

##### Example 5

Secret `AABC` and guess `ADFA` must be evaluated to: `rightPosition = 1, wrongPosition = 1`.
The first `A` letter is guessed correctly with its position. If we remove this letter from consideration
(comparing the remaining `ABC` and `DFA`), we find the second `A` letter which is guessed correctly
but stays at the wrong position.

##### Example 6

Secret `AABC` and guess `DFAA` must be evaluated to: `rightPosition = 0, wrongPosition = 2`.
No letters are guessed correctly concerning their positions. 
When we compare the letters without positions, `A` is guessed correctly.
Since `A` is present twice in both guess and secret, it must be counted two times.

##### Example 7

Secret `AABC` and guess `DEFA` must be evaluated to: `rightPosition = 0, wrongPosition = 1`.
The letter `A` occurs only once in the second string, that's why it counted only once as staying at the wrong position.

After implementing the task for repeated letters, run `MastermindTestDifferentLetters` to make sure 
it works correctly.

## Week 3

### Nice String

A string is nice if *at least two* of the following conditions are satisfied:

1. It doesn't contain substrings `bu`, `ba` or `be`;
2. It contains at least three vowels (vowels are `a`, `e`, `i`, `o` and `u`);
3. It contains a double letter (at least two similar letters following one
another), like `b` in `"abba"`.

Your task is to check whether a given string is nice. 
Strings for this task will consist of lowercase letters only.
Note that for the purpose of this task, you don't need to consider 'y' as a vowel.

Note that any two conditions might be satisfied to make a string nice.
For instance, `"aei"` satisfies only the conditions #1 and #2, and
```"nn"` satisfies the conditions #1 and #3, which means both strings are nice.

#### Example 1

`"bac"` isn't nice. No conditions are satisfied: it contains a `ba` substring,
contains only one vowel and no doubles.

#### Example 2

`"aza"` isn't nice. Only the first condition is satisfied, but the string
doesn't contain enough vowels or doubles.

#### Example 3

`"abaca"` isn't nice. The second condition is satisfied: it contains three
vowels `a`, but the other two aren't satisfied: it contains `ba` and no
doubles.

#### Example 4

`"baaa"` is nice. The conditions #2 and #3 are satisfied: it contains
three vowels `a` and a double `a`. 

#### Example 5

`"aaab"` is nice, because all three conditions are satisfied.

Run `TestNiceString` to check your solution.

### Taxi Park

The `TaxiPark` class stores information about registered drivers, passengers,
and their trips. Your task is to implement six functions which collect
different statistics about the data.

#### Task 1

```
fun TaxiPark.findFakeDrivers(): Collection<Driver>
```

Find all the drivers who didn't perform any trips.


#### Task 2

```
fun TaxiPark.findFaithfulPassengers(minTrips: Int): List<Passenger>
```

Find all the clients who completed at least the given number of trips.

#### Task 3

```
fun TaxiPark.findFrequentPassengers(driver: Driver): List<Passenger>
```

Find all the passengers who were driven by a certain driver more than once.

#### Task 4

```
fun TaxiPark.findSmartPassengers(): Collection<Passenger>
```

If we consider "smart", a passenger who had a discount for the majority of the trips they made or took part in
(including the trips with more than one passenger), find all the "smart" passengers.
A "smart" passenger should have strictly more trips with discount than trips without discount,
the equal amounts of trips with and without discount isn't enough.

Note that the discount can't be `0.0`, it's always non-zero if it's recorded. 

#### Task 5

```
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange?
```

Find the most frequent trip duration period among minute periods 0..9, 10..19, 20..29, and so on.
Return any suitable period if many are the most frequent, return `null` if there're no trips.
 

#### Task 6

```
fun TaxiPark.checkParetoPrinciple(): Boolean
```

Check whether no more than 20% of the drivers contribute 80% of the income.
The function should return true if the top 20% drivers (meaning the top 20% best
performers) represent 80% or more of all trips total income, or false if not.
The drivers that have no trips should be considered as contributing zero income. 
If the taxi park contains no trips, the result should be `false`.

For example, if there're 39 drivers in the taxi park, we need to check that no more than
20% of the most successful ones, which is seven drivers (39 * 0.2 = 7.8), contribute
at least 80% of the total income. Note that eight drivers out of 39 is 20.51% which
is more than 20%, so we check the income of seven the most successful drivers.

To find the total income sum up all the trip costs. Note that the discount is already
applied while calculating the cost.  


## Week 4

### Rational Numbers

Your task is to implement a class `Rational` representing rational numbers.
A rational number is a number expressed as a ratio `n/d` , where `n` (numerator)
and `d` (denominator) are integer numbers, except that `d` cannot be zero.
If the denominator is zero, you can throw an exception on creating a new
instance of the class, e.g. `IllegalArgumentException`.

Examples of rational numbers are `1/2`, `2/3`, `117/1098`, and `2/1` (which
can alternatively be written simply as `2`).
Rational numbers are represented exactly, without rounding or
approximation, which gives them the advantage compared to floating-point numbers. 

Your task it to model the behavior of rational numbers, including allowing them
to be added, subtracted, multiplied, divided and compared.
All arithmetic and comparison operations must be available for rationals:
`+`, `-`, `*`, `/`, `==`, `<`, `>=` etc.
Check whether a number belongs to a range should also be possible:
`1/2 in 1/3..2/3` should return true.

The `Rational` class should contain a numerator and denominator which might be
unlimited numbers, so use `java.math.BigInteger` class for storing them.

The rational numbers must be compared to their "normalized" forms:
for example, `1/2` should be equal to `2/4`, or `117/1098` to `13/122`.
The string representation of a rational must be also given in the normalized form.
Note that the denominator `1` must be omitted, so `2/1` must be printed as `"2"`.
The denominator must be always positive in the normalized form.
If the negative rational is normalized, then only the numerator can be negative, e.g.
the normalized form of `1/-2` should be `-1/2`.

Note that you can use `BigInteger.gcd` to find the greatest common divisor
used in the normalized form calculation.  

You need to support two ways to create rationals. The first one is to convert
a string representation to a rational directly, like in `"1/2".toRational()`.
Converting an integer number should also be possible, and `1` should be used
as denominator by default: `"23".toRational()` is the same as `"23/1".toRational()`.

The alternative way to create a rational is to use `divBy` infix function
like in `1 divBy 2`. The receiver and the argument might be of types `Int`,
`Long`, or `BigInteger.`


#### Examples

All the following equality checks must be evaluated to `true`:

```
val half = 1 divBy 2
val third = 1 divBy 3

val sum: Rational = half + third
5 divBy 6 == sum

val difference: Rational = half - third
1 divBy 6 == difference

val product: Rational = half * third
1 divBy 6 == product

val quotient: Rational = half / third
3 divBy 2 == quotient

val negation: Rational = -half
-1 divBy 2 == negation

(2 divBy 1).toString() == "2"
(-2 divBy 4).toString() == "-1/2"
"117/1098".toRational().toString() == "13/122"

val twoThirds = 2 divBy 3
half < twoThirds

half in third..twoThirds

2000000000L divBy 4000000000L == 1 divBy 2

"912016490186296920119201192141970416029".toBigInteger() divBy
    "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
```

  
### Board

Your task is to implement interfaces `SquareBoard` and `GameBoard`.

#### SquareBoard

SquareBoard stores the information about the square board and all the cells on it.
It allows the retrieval of a cell by its indexes, parts of columns and rows on a board,
or a specified neighbor of a cell.

Note that the numbering of cells starts with 1 (not 0).

A board of width two consists of the following cells:
```
(1, 1) (1, 2)
(2, 1) (2, 2)
```

For the following examples, we'll use this board of width 2:
```
val board = createSquareBoard(2)
```

If you call `board.getCellOrNull(3, 3)` for such a board, you'll get `null` as
the result, because the board doesn't have a cell with such coordinates.
The function `Board.getCell` should throw `IllegalArgumentException` for
incorrect values of `i` and `j`.

You can write `board.getRow(1, 1..2)` or `board.getRow(1, 2 downTo 1)`,
and you'll get the lists of cells `[(1, 1), (1, 2)]` and `[(1, 2), (1, 1)]`
accordingly.

Note how using the range `2 downTo 1` returns a row in a reversed order.
You can use any range to get a part of a column or a row.

Note that `getRow` and `getColumn` should return a list containing only
the cells that belong to the board if the range is larger than the board limits
and ignore other indexes,
thus, `board.getRow(1, 1..10)` should return `[(1, 1), (1, 2)]`.  

The neighbors of a cell `(1, 1)`, depending on the direction should be:
```
Direction.UP - null     
Direction.LEFT - null     
Direction.DOWN - (2, 1) 
Direction.RIGHT - (1, 2)
```

Create only `width * width` cells; all the functions working with cells
should return existing cells instead of creating new ones. 

#### GameBoard

GameBoard allows you to store values in board cells, update them,
and enquire about stored values (like `any`,
`all` etc.)
Note that GameBoard extends SquareBoard.

Don't store a value in a `Cell`: data class `Cell` is intended to be immutable
and only store the coordinates.
You can store values separately, for instance, in a map from `Cell` to stored values type. 

See `TestSquareBoard` and `TestGameBoard` for examples.

