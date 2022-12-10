import java.util.LinkedList
import kotlin.math.abs

fun main() {

    fun checkCycle(cycle: Int) :Boolean {
        return cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220
    }

    fun part1(input: List<String>): Int {
        var cycle = 0
        var x = 1
        var sum = 0
        input.forEach {
            cycle++
            if(checkCycle(cycle)) {
                sum += cycle * x
            }
            if( it.startsWith("addx")) {
                cycle++
                if(checkCycle(cycle)) {
                    sum += cycle * x
                }
                x += it.split(" ")[1].toInt()
            }
        }
        return sum
    }

    fun printCycle(cycle: Int, x: Int) {
        if (abs((cycle % 40) - x) <= 1) {
            print("#")
        } else {
            print(".")
        }
        if ((cycle +1) % 40 == 0) {
            println()
        }
    }

    fun part2(input: List<String>): Int {
        var cycle = 0
        var x = 1
        input.forEach {
            printCycle(cycle, x)
            cycle++
            if( it.startsWith("addx")) {
                printCycle(cycle, x)
                cycle++
                x += it.split(" ")[1].toInt()
            }
        }
        return 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    println("Test")
    val part1 = part1(testInput)
    println(part1)
    val part2 = part2(testInput)
    println(part2)
    check(part1 == 13140)
    check(part2 == 1)

    println("Waarde")
    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
