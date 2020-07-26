package rationals

import java.math.BigInteger
import java.math.BigInteger.*

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}

class Rational(val numerator: BigInteger, val denominator: BigInteger) : Comparable<Rational> {

    init {
        if (denominator == ZERO) throw IllegalArgumentException()
    }

    operator fun plus(r: Rational): Rational = (numerator.times(r.denominator).plus(r.numerator.times(denominator))) divBy (r.denominator.times(denominator))

    operator fun minus(r: Rational): Rational = (numerator.times(r.denominator).minus(r.numerator.times(denominator))) divBy (r.denominator.times(denominator))

    operator fun times(r: Rational): Rational = numerator.times(r.numerator) divBy (r.denominator.times(denominator))

    operator fun div(r: Rational): Rational = numerator.times(r.denominator) divBy (denominator.times(r.numerator))

    operator fun unaryMinus(): Rational = Rational(numerator.negate(), denominator)

    override fun compareTo(other: Rational): Int = numerator.times(other.denominator).compareTo(other.numerator.times(denominator))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return when (other) {
            is Rational -> {
                val (normalizedThis, normalizedOther) = normalize(this, other)
                normalizedThis.numerator.toDouble().div(normalizedThis.denominator.toDouble()) ==
                        normalizedOther.numerator.toDouble().div(normalizedOther.denominator.toDouble())
            }
            else -> false
        }
    }

    override fun hashCode(): Int = 31 * numerator.hashCode() + denominator.hashCode()

    override fun toString(): String {
        return this.normalize().let {
            when (it.denominator) {
                ONE -> it.numerator.toString()
                else -> "${it.numerator}/${it.denominator}"
            }
        }
    }
}

infix fun Int.divBy(rational: Int): Rational = Rational(toBigInteger(), rational.toBigInteger())

infix fun Long.divBy(rational: Long): Rational = Rational(toBigInteger(), rational.toBigInteger())

infix fun BigInteger.divBy(rational: BigInteger): Rational = Rational(this, rational)

fun String.toRational(): Rational {
    val numbers = this.split("/")
    return when (numbers.size) {
        1 -> Rational(numbers.first().toBigInteger(), ONE)
        2 -> Rational(numbers.first().toBigInteger(), numbers.last().toBigInteger())
        else -> throw IllegalArgumentException()
    }
}

private fun normalize(r1: Rational, r2: Rational) = r1.normalize() to r2.normalize()

private fun Rational.normalize(): Rational = numerator.gcd(denominator).let {
    when {
        denominator < ZERO -> Rational(numerator.negate().div(it), denominator.negate().div(it))
        else -> Rational(numerator.div(it), denominator.div(it))
    }
}
