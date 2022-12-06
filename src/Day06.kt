import java.util.*
import kotlin.collections.ArrayList

fun main() {

    fun findStartChar(input: String, uniqueChars: Int) : Int {
        for(i in 0 until input.length - uniqueChars) {
            val charSet = input.substring(i, i + uniqueChars).toSet()
            if(charSet.size == uniqueChars) {
                return i + uniqueChars
            }
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        return findStartChar(input.get(0), 4)
    }

    fun part2(input: List<String>): Int {
        return findStartChar(input.get(0), 14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    println("Test")
    println(part1(testInput))
    println(part2(testInput))
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    println("Waarde")
    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
