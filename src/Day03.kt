fun main() {
    val charslist = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    fun part1(input: List<String>): Int {
        return input.map {
            val middle = it.length/2
            val first = it.substring(0 until middle)
            val second = it.substring(middle)
            val intersect = first.toCharArray().intersect(second.toSet())
            charslist.indexOf(intersect.first()) + 1
        }.sum()
    }

    fun part2(input: List<String>): Int {
        var sum = 0;
        for (i in input.indices step 3) {
            val intersect = input[i].toCharArray().intersect(input[i+1].toSet()).intersect(input[i+2].toSet())
            sum += charslist.indexOf(intersect.first()) + 1
        }
        return sum;
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println("Test")
    println(part1(testInput))
    println(part2(testInput))
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    println("Waarde")
    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
