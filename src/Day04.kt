
fun main() {

    fun toRange(str: String): IntRange = str
        .split("-")
        .let { (a, b) -> a.toInt()..b.toInt() }

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val split = it.split(",")
            val first = toRange(split.get(0))
            val second = toRange(split.get(1))
            val intersection = first.intersect(second)
            if(intersection.isNotEmpty() && (intersection == first.toSet() || intersection == second.toSet())) {
                sum++
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val split = it.split(",")
            val first = toRange(split.get(0))
            val second = toRange(split.get(1))
            val intersection = first.intersect(second)
            if(intersection.isNotEmpty()) {
                sum++
            }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    println("Test")
    println(part1(testInput))
    println(part2(testInput))
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    println("Waarde")
    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
