import java.util.*
import kotlin.collections.ArrayList

fun main() {

    fun fillStacks(it: String, buckets: TreeMap<Int, LinkedList<String>>) {
        val chunkes = it.chunked(4)
        chunkes.forEachIndexed { index, element ->
            val letter = element.substring(1..1)
            if (letter.isNotBlank() && letter.toIntOrNull() == null) {
                val stack = buckets.computeIfAbsent(index + 1) { LinkedList<String>() }
                stack.add(letter)
            }
        }
    }

    fun part1(input: List<String>): String {
        val buckets = TreeMap<Int, LinkedList<String>>()
        var moving = false

        input.forEach {
            if (moving) {
                val inputLineRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()
                val (move, from, to) = inputLineRegex
                    .matchEntire(it)
                    ?.destructured
                    ?: throw IllegalArgumentException("Incorrect input line $it")
                for (x in 0 until move.toInt()) {
                    buckets.get(to.toInt())?.addFirst(buckets.get(from.toInt())?.remove())
                }
            } else {
                if (it.isBlank()) {
                    moving = true
                } else {
                    fillStacks(it, buckets)
                }
            }
        }
        return buckets.values.joinToString("") { it.peek() }
    }

    fun part2(input: List<String>): String {
        val buckets = TreeMap<Int, LinkedList<String>>()
        var moving = false

        input.forEach {
            if (moving) {
                val inputLineRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()
                val (move, from, to) = inputLineRegex
                    .matchEntire(it)
                    ?.destructured
                    ?: throw IllegalArgumentException("Incorrect input line $it")

                val removeList = ArrayList<String>()
                for (x in 0 until move.toInt()) {
                    val element = buckets.get(from.toInt())?.remove()
                    removeList.add(element.orEmpty())
                }
                buckets.get(to.toInt())?.addAll(0, removeList)
            } else {
                if (it.isBlank()) {
                    moving = true
                } else {
                    fillStacks(it, buckets)
                }
            }
        }
        return buckets.values.joinToString("") { it.peek() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    println("Test")
    println(part1(testInput))
    println(part2(testInput))
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    println("Waarde")
    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
