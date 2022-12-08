
fun main() {

    fun isGreaterThanAlPrevious(tree: Char, index: Int, line: String): Boolean {
        return line.subSequence(0, index).all { it.code < tree.code }
    }

    fun checkLine(line: String, checked: Array<Array<Boolean>>, lineIndex: Int, columnMode: Boolean, reversed : Boolean) {
        for ((index, tree) in line.subSequence(1, line.length - 1).withIndex()) {
            if (isGreaterThanAlPrevious(tree, index + 1, line)) {
                if (columnMode) {
                    if(reversed) {
                        checked[checked.size - 1 - index][lineIndex] = true
                    } else {
                        checked[index][lineIndex] = true
                    }
                } else {
                    if(reversed) {
                        checked[lineIndex][checked[lineIndex].size - 1 - index] = true
                    } else {
                        checked[lineIndex][index] = true
                    }
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val checked = Array(input.get(0).length - 2) {Array(input.size - 2) { false } }
        var visible = 0
        visible += input.size * 2
        visible += (input.get(0).length * 2) - 4

        for((index, line) in input.subList(1, input.size - 1).withIndex()) {
            checkLine(line, checked, index, false, false)
            checkLine(line.reversed(), checked, index, false, true)
        }

        val rotated = Array(input.get(0).length) {Array(input.size) { Char(0) } }

        for((lineIndex, line) in input.withIndex()) {
            for ((index, tree) in line.withIndex()) {
                rotated[index][lineIndex] = tree
            }
        }

        for((index, column) in rotated.sliceArray(1 until rotated.size - 1).withIndex()) {
            val line = column.joinToString("")
            checkLine(line, checked, index, true, false)
            checkLine(line.reversed(), checked, index, true, true)
        }

        return visible + checked.sumOf { it -> it.count { it } }
    }

    fun part2(input: List<String>): Int {
        var senic = 0;
        for((index, line) in input.withIndex()) {
            for((i2, tree) in line.withIndex()) {
                var countTop = 0
                for(j in (0 until index).reversed() ) {
                    countTop++
                    if(input[j][i2].code >= tree.code) {
                        break
                    }
                }
                var countLeft = 0
                for(j in (0 until i2).reversed() ) {
                    countLeft++
                    if(line[j].code >= tree.code)  {
                        break
                    }
                }
                var countRight = 0
                for(j in i2 + 1 until line.length) {
                    countRight++
                    if(line[j].code >= tree.code) {
                        break
                    }
                }
                var countBottom = 0
                for(j in index + 1 until  input.size) {
                    countBottom++
                    if (input[j][i2].code >= tree.code){
                        break
                    }
                }
                val multi = countTop * countLeft * countBottom * countRight
                if(multi > senic) {
                    senic = multi
                }
            }
        }
        return senic
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    println("Test")
    println(part1(testInput))
    println(part2(testInput))
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    println("Waarde")
    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
