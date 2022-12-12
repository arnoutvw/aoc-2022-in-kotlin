import com.notkamui.keval.Keval

fun main() {

    data class Monkey(val numbers: MutableList<Long>, val operation: String, val divisibleBy: Int, val monkyIfTrue: Int, val monkeyIfFalse: Int, var inspections: Long = 0)

    fun part1(input: List<String>, monkeys: MutableMap<Int, Monkey>): Int {
        for(i in 1..20) {
            for (m in 0 until monkeys.size) {
                val monkey = monkeys[m]
                if(monkey != null) {
                    monkey.numbers.forEach {
                        val res = Keval.eval(monkey.operation.replace("old", it.toString())).toInt() / 3
                        if (res % monkey.divisibleBy == 0) {
                            monkeys[monkey.monkyIfTrue]?.numbers?.add(res.toLong())
                        } else {
                            monkeys[monkey.monkeyIfFalse]?.numbers?.add(res.toLong())
                        }
                    }
                    monkey.inspections += monkey.numbers.size
                    monkey.numbers.clear()
                }

            }
        }
        return monkeys.values.map { it.inspections }.sortedDescending().take(2).reduce { i, j -> i*j }.toInt()
    }

    fun part2(input: List<String>, monkeys: MutableMap<Int, Monkey>): Long {
        val mod = monkeys.values.map { it.divisibleBy }.reduce { a, b -> a * b }
        for(i in 0 until 10000) {
            for (m in 0 until monkeys.size) {
                val monkey = monkeys[m]
                if(monkey != null) {
                    monkey.numbers.forEach {
                        val res = Keval.eval(monkey.operation.replace("old", it.toString())).toLong() % mod
                        if (res % monkey.divisibleBy.toLong() == 0L) {
                            monkeys[monkey.monkyIfTrue]?.numbers?.add(res)
                        } else {
                            monkeys[monkey.monkeyIfFalse]?.numbers?.add(res)
                        }
                    }
                    monkey.inspections += monkey.numbers.size
                    monkey.numbers.clear()
                }

            }
        }
        return monkeys.values.map { it.inspections }.sortedDescending().take(2).reduce { i, j -> i*j }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    val testMonkeys = mutableMapOf(0 to Monkey(mutableListOf(79, 98), "old * 19", 23, 2, 3),
        1 to Monkey(mutableListOf(54, 65, 75, 74), "old + 6", 19, 2, 0),
        2 to Monkey(mutableListOf(79, 60, 97), "old * old", 13, 1, 3),
        3 to Monkey(mutableListOf(74), "old + 3", 17, 0, 1))
    println("Test")
    //val part1 = part1(testInput, testMonkeys)
    //println(part1)
    val part2 = part2(testInput, testMonkeys)
    println(part2)
    //check(part1 == 10605)
    check(part2 == 2713310158)

    println("Waarde")
    val input = readInput("Day11")
    val realMonkeys = mutableMapOf(0 to Monkey(mutableListOf(56, 52, 58, 96, 70, 75, 72), "old * 17", 11, 2, 3),
        1 to Monkey(mutableListOf(75, 58, 86, 80, 55, 81), "old + 7", 3, 6, 5),
        2 to Monkey(mutableListOf(73, 68, 73, 90), "old * old", 5, 1, 7),
        3 to Monkey(mutableListOf(72, 89, 55, 51, 59), "old + 1", 7, 2, 7),
        4 to Monkey(mutableListOf(76, 76, 91), "old * 3", 19, 0, 3),
        5 to Monkey(mutableListOf(88), "old + 4", 2, 6, 4),
        6 to Monkey(mutableListOf(64, 63, 56, 50, 77, 55, 55, 86), "old + 8", 13, 4, 0),
        7 to Monkey(mutableListOf(79, 58), "old + 6", 17, 1, 5),
    )
    //println(part1(input, realMonkeys))
    println(part2(input, realMonkeys))
}
