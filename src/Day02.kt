import java.lang.IllegalArgumentException

fun main() {

    fun part1(input: List<String>): Int {
        var score = 0
        input.forEach {
            val split = it.split(" ")
            val oppenent = Hand.getHand(split.get(0))
            val own = Hand.getHand(split.get(1))
            score += own.waarde + outcome(oppenent, own)
        }
        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0
        input.forEach {
            val split = it.split(" ")
            val oppenent = Hand.getHand(split.get(0))
            val choice = outcomePart2(oppenent, split.get(1))
            score += choice.waarde + outcome(oppenent, choice)
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    println("Test")
    println(part1(testInput))
    println(part2(testInput))
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    println("Waarde")
    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

enum class Hand {
    ROCK {
        override val beats: Hand get() = SCISSORS
        override val waarde: Int get() = 1
    },
    PAPER {
        override val beats: Hand get() = ROCK
        override val waarde: Int get() = 2
    },
    SCISSORS {
        override val beats: Hand get() = PAPER
        override val waarde: Int get() = 3
    };

    abstract val beats: Hand
    abstract val waarde: Int

    companion object {
        fun getHand(waarde: String): Hand {
            return when(waarde) {
                "A" -> ROCK
                "B" -> PAPER
                "C" -> SCISSORS
                "X" -> ROCK
                "Y" -> PAPER
                "Z" -> SCISSORS
                else -> throw IllegalAccessException("Onbekend: $waarde")
            }
        }
    }
}

fun outcome(p1: Hand, p2: Hand): Int {
    if(p1 == p2) {
        return 3;
    }

    if(p2.beats == p1) {
        return 6;
    }

    return 0;
}

fun outcomePart2(p1: Hand, p2: String): Hand {
    return when (p2) {
        "X" -> {
            p1.beats
        }
        "Y" -> {
            p1
        }
        else -> {
            when (p1) {
                Hand.ROCK -> Hand.PAPER
                Hand.PAPER -> Hand.SCISSORS
                Hand.SCISSORS -> Hand.ROCK
            }
        }
    }
}
