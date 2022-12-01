fun main() {
    fun part1(input: List<String>): Int {
        var maxCals = 0;
        var cals = 0;
        input.forEach {
            if(it.isBlank()) {
                if(cals > maxCals) {
                    maxCals = cals;
                }
                cals = 0;
            } else {
                cals += Integer.valueOf(it);
            }
        }
        return maxCals;
    }

    fun part2(input: List<String>): Int {
        val cals = ArrayList<Int>();
        var cal = 0;
        input.forEach {
            if(it.isBlank()) {
                cals.add(cal)
                cal = 0;
            } else {
                cal += Integer.valueOf(it);
            }
        }
        return cals.sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
