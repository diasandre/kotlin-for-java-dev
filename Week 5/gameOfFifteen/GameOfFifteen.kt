package games.gameOfFifteen

import board.Cell
import board.Direction
import board.createGameBoard
import games.game.Game
import java.util.*

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)
    override fun initialize() {
        board.getAllCells().zip(initializer.initialPermutation).forEach {
            board[it.first] = it.second
        }
    }

    override fun canMove() = true

    override fun hasWon(): Boolean = board.getAllCells().mapNotNull(board::get).let(::orderAndGetMoves).sum() == 0

    private fun getEmptyCell() = board.find { it == null }

    private fun swap(from: Cell, to: Cell) {
        board[from] = board[to]
        board[to] = null
    }

    override fun processMove(direction: Direction) {
        getEmptyCell()?.let { emptyCell ->
            board.run {
                emptyCell.getNeighbour(direction.opposite())
                        .takeIf(Objects::nonNull)
                        ?.let { neighbour -> swap(emptyCell, neighbour) }
            }
        }
    }

    override fun get(i: Int, j: Int): Int? = board[board.getCell(i, j)]

    private fun Direction.opposite(): Direction = when (this) {
        Direction.LEFT -> Direction.RIGHT
        Direction.UP -> Direction.DOWN
        Direction.RIGHT -> Direction.LEFT
        Direction.DOWN -> Direction.UP
    }

}


