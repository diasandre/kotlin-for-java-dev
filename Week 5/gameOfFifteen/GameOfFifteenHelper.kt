package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */

fun swapRight(initialIndex: Int, expectedIndex: Int, result: MutableList<Int>): Int {
    var moves = 0
    (initialIndex until expectedIndex).forEach { actualIndex ->
        val firstValue = result[actualIndex]
        if (actualIndex + 1 <= result.size) {
            result[actualIndex] = result[actualIndex + 1]
            result[actualIndex + 1] = firstValue
            moves++
        }
    }
    return moves
}

fun swapLeft(initialIndex: Int, expectedIndex: Int, result: MutableList<Int>): Int {
    var moves = 0
    (initialIndex downTo expectedIndex).forEach { actualIndex ->
        val firstValue = result[actualIndex]
        if (actualIndex - 1 >= 0) {
            result[actualIndex] = result[actualIndex - 1]
            result[actualIndex - 1] = firstValue
            moves++
        }
    }
    return moves
}

fun swapValuesIfNecessary(permutation: List<Int>, result: MutableList<Int>, expectedOrder: List<Int>): List<Int> {
    return IntRange(0, permutation.size - 1).mapNotNull { actualIndex ->
        val actualValueInPosition = result[actualIndex]
        val expectedIndex = expectedOrder.indexOf(actualValueInPosition)
        val numberOfMoves = expectedIndex - actualIndex
        when {
            numberOfMoves > 0 -> swapRight(actualIndex, expectedIndex, result)
            numberOfMoves < 0 -> swapLeft(actualIndex, expectedIndex, result)
            else -> null
        }
    }
}

fun orderAndGetMoves(permutation: List<Int>): List<Int> {
    val expectedOrder = permutation.sortedBy { it }
    val result = permutation.toMutableList()
    val moves = mutableListOf<Int>()
    while (result != expectedOrder) {
        swapValuesIfNecessary(permutation, result, expectedOrder).let { moves.addAll(it) }
    }
    return moves
}

fun isEven(permutation: List<Int>): Boolean = orderAndGetMoves(permutation).sum() % 2 == 0