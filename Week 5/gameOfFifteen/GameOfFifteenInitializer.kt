package games.gameOfFifteen

import java.util.*

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    private val items = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)

    private fun List<Int>.getShuffledIfIsEven(): List<Int>? = this.shuffled().takeIf(::isEven)

    override val initialPermutation by lazy {
        var result = items.getShuffledIfIsEven()
        while (result == null) {
            result = items.getShuffledIfIsEven()
        }
        result ?: emptyList()
    }
}

