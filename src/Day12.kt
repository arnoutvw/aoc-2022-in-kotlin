import java.lang.Math.abs

typealias GridPosition = Pair<Int, Int>
typealias Barrier = Set<GridPosition>

const val MAX_SCORE = 99999999

fun main() {

    abstract class Grid() {

        open fun heuristicDistance(start: GridPosition, finish: GridPosition): Int {
            val dx = abs(start.first - finish.first)
            val dy = abs(start.second - finish.second)
            return (dx + dy) + (-2) * minOf(dx, dy)
        }

        abstract fun getNeighbours(position: GridPosition): List<GridPosition>

        open fun moveCost(from: GridPosition, to: GridPosition) = 1
    }

    class SquareGrid(width: Int, height: Int, val input: List<String>) : Grid() {

        private val heightRange: IntRange = (0 until height)
        private val widthRange: IntRange = (0 until width)

        private val validMoves = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))

        override fun getNeighbours(position: GridPosition): List<GridPosition> {
            val n = validMoves
                .map { GridPosition(position.first + it.first, position.second + it.second) }
                .filter { inGrid(it) }
                .filter { var current = input[position.second][position.first]
                    if(current == 'S') {
                        current = 'a'
                    }
                    var target = input[it.second][it.first]
                    if (target == 'E') {
                        target = '{'
                    }
                    val dif = target.code - current.code
                    dif <= 1 }

            return n
        }

        private fun inGrid(it: GridPosition) = (it.first in widthRange) && (it.second in heightRange)
    }

    /**
     * Implementation of the A* Search Algorithm to find the optimum path between 2 points on a grid.
     *
     * The Grid contains the details of the barriers and methods which supply the neighboring vertices and the
     * cost of movement between 2 cells.  Examples use a standard Grid which allows movement in 8 directions
     * (i.e. includes diagonals) but alternative implementation of Grid can be supplied.
     *
     */
    fun aStarSearch(start: GridPosition, finish: GridPosition, grid: Grid): Pair<List<GridPosition>, Int> {

        /**
         * Use the cameFrom values to Backtrack to the start position to generate the path
         */
        fun generatePath(currentPos: GridPosition, cameFrom: Map<GridPosition, GridPosition>): List<GridPosition> {
            val path = mutableListOf(currentPos)
            var current = currentPos
            while (cameFrom.containsKey(current)) {
                current = cameFrom.getValue(current)
                path.add(0, current)
            }
            return path.toList()
        }

        val openVertices = mutableSetOf(start)
        val closedVertices = mutableSetOf<GridPosition>()
        val costFromStart = mutableMapOf(start to 0)
        val estimatedTotalCost = mutableMapOf(start to grid.heuristicDistance(start, finish))

        val cameFrom = mutableMapOf<GridPosition, GridPosition>()  // Used to generate path by back tracking

        while (openVertices.size > 0) {

            val currentPos = openVertices.minBy { estimatedTotalCost.getValue(it) }!!

            // Check if we have reached the finish
            if (currentPos == finish) {
                // Backtrack to generate the most efficient path
                val path = generatePath(currentPos, cameFrom)
                return Pair(path, estimatedTotalCost.getValue(finish)) // First Route to finish will be optimum route
            }

            // Mark the current vertex as closed
            openVertices.remove(currentPos)
            closedVertices.add(currentPos)

            grid.getNeighbours(currentPos)
                .filterNot { closedVertices.contains(it) }  // Exclude previous visited vertices
                .forEach { neighbour ->
                    val score = costFromStart.getValue(currentPos) + grid.moveCost(currentPos, neighbour)
                    if (score < costFromStart.getOrDefault(neighbour, MAX_SCORE)) {
                        if (!openVertices.contains(neighbour)) {
                            openVertices.add(neighbour)
                        }
                        cameFrom.put(neighbour, currentPos)
                        costFromStart.put(neighbour, score)
                        estimatedTotalCost.put(neighbour, score + grid.heuristicDistance(neighbour, finish))
                    }
                }

        }

        throw IllegalArgumentException("No Path from Start $start to Finish $finish")
    }


    fun part1(input: List<String>): Int {
        var start = GridPosition(0,0)
        var end = GridPosition(7,7)
        input.withIndex().forEach {(y, line) ->
            line.withIndex().forEach { (x, char) ->
                if(char == 'S') {
                    start = GridPosition(x, y)
                } else if (char == 'E') {
                    end = GridPosition(x, y)
                }
            }
        }
        val (path, cost) = aStarSearch(start, end, SquareGrid(input[0].length,input.size, input))
        return cost
    }

    fun part2(input: List<String>): Int {
        val startnodes = mutableListOf<GridPosition>()
        var end = GridPosition(7,7)
        input.withIndex().forEach {(y, line) ->
            line.withIndex().forEach { (x, char) ->
                if(char == 'S' || char == 'a') {
                    startnodes.add(GridPosition(x, y))
                } else if (char == 'E') {
                    end = GridPosition(x, y)
                }
            }
        }

        return startnodes.map {
            try {
                val (path, cost) = aStarSearch(it, end, SquareGrid(input[0].length,input.size, input))
                cost
            } catch (e: Exception) {
                MAX_SCORE
            }
        }.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    println("Test")
    val part1 = part1(testInput)
    println(part1)
    val part2 = part2(testInput)
    println(part2)
    check(part1 == 31)
    check(part2 == 29)

    println("Waarde")
    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
