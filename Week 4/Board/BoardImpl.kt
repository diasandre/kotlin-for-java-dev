package board

import board.Direction.*
import java.lang.IllegalArgumentException

fun IntProgression.isDescending() = this.first > this.last

fun createSquareBoard(width: Int): SquareBoard = BoardImpl(width)

fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

class GameBoardImpl<T>(width: Int) : BoardImpl(width), GameBoard<T> {

    private val cellValues = mutableMapOf<Cell, T?>()

    init {
        boardCells.values.forEach {
            cellValues[it] = null
        }
    }

    override fun get(cell: Cell): T? = cellValues[cell]

    override fun set(cell: Cell, value: T?) {
        cellValues[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = cellValues.filterValues(predicate::invoke).keys

    override fun find(predicate: (T?) -> Boolean): Cell? = cellValues.filter{ predicate.invoke(it.value) }.keys.firstOrNull()

    override fun any(predicate: (T?) -> Boolean): Boolean = cellValues.values.any(predicate)

    override fun all(predicate: (T?) -> Boolean): Boolean = cellValues.values.all(predicate)
}

open class BoardImpl(final override val width: Int) : SquareBoard {

    private val cells = mutableListOf<Pair<Int, Int>>()
    val boardCells: MutableMap<Pair<Int, Int>, Cell> = mutableMapOf()

    init {
        IntRange(1, width).forEach { row ->
            IntRange(1, width).forEach { column ->
                cells.add(Pair(row, column))
            }
        }

        cells.forEach {
            boardCells[it] = Cell(it.first, it.second)
        }
    }

    private fun findPairCell(i: Int, j: Int) = cells.find { it.first == i && it.second == j }
    private fun filterPairCellByRow(i: Int): List<Pair<Int, Int>> = cells.filter { it.first == i }
    private fun filterPairCellByColumn(j: Int): List<Pair<Int, Int>> = cells.filter { it.second == j }

    override fun getCellOrNull(i: Int, j: Int): Cell? = findPairCell(i, j)?.let { boardCells[it] }

    override fun getCell(i: Int, j: Int): Cell = getCellOrNull(i, j) ?: throw IllegalArgumentException()

    override fun getAllCells(): Collection<Cell> = boardCells.map { (_, cell) -> cell }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> = filterPairCellByRow(i)
            .filter { it.second in jRange }
            .map { boardCells.getValue(it) }
            .let { if (jRange.isDescending()) it.reversed() else it }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> = filterPairCellByColumn(j)
            .filter { it.first in iRange }
            .map { boardCells.getValue(it) }
            .let { if (iRange.isDescending()) it.reversed() else it }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> getCellOrNull(i - 1, j)
            DOWN -> getCellOrNull(i + 1, j)
            RIGHT -> getCellOrNull(i, j + 1)
            LEFT -> getCellOrNull(i, j - 1)
        }
    }
}

