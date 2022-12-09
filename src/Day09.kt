import java.util.LinkedList
import kotlin.math.abs

fun main() {

    data class Point(var x: Int, var y: Int)

    fun isTouching(head: Point, tail: Point) :Boolean {
        if (abs(head.x - tail.x) <= 1 && abs(head.y - tail.y) <= 1) {
            return true
        }
        return false
    }

    fun part1(input: List<String>): Int {
        val visited = LinkedHashSet<Point>()
        val head = Point(0,0)
        val tail = Point(0,0)
        visited.add(Point(tail.x, tail.y))

        input.forEach {
            val split = it.split(" ")
            for(i in 0 until split.get(1).toInt()) {
                when(split[0]) {
                    "U" -> {
                        head.x++
                        if(!isTouching(head, tail)) {
                            tail.x = head.x - 1
                            tail.y = head.y
                            visited.add(Point(tail.x, tail.y))
                        }
                    }
                    "D" -> {
                        head.x--
                        if(!isTouching(head, tail)) {
                            tail.x = head.x + 1
                            tail.y = head.y
                            visited.add(Point(tail.x, tail.y))
                        }
                    }
                    "L" -> {
                        head.y--
                        if(!isTouching(head, tail)) {
                            tail.y = head.y + 1
                            tail.x = head.x
                            visited.add(Point(tail.x, tail.y))
                        }
                    }
                    "R" -> {
                        head.y++
                        if(!isTouching(head, tail)) {
                            tail.y = head.y - 1
                            tail.x = head.x
                            visited.add(Point(tail.x, tail.y))
                        }
                    }
                }
            }
        }

        return visited.count()
    }

    operator fun Point.plus(o: Point) = Point(this.x + o.x, this.y + o.y)

    fun part2(input: List<String>): Int {
        val visited = LinkedHashSet<Point>()
        val rope = MutableList(10) { Point(0, 0) }
        visited.add(Point(0, 0))

        val lookup = mapOf("D" to Point(0, 1), "U" to Point(0, -1), "L" to Point(-1, 0), "R" to Point(1, 0))

        input.forEach {
            val split = it.split(" ")
            for (j in 1..split[1].toInt()) {
                rope[0] = rope[0] + lookup[split[0]]!!
                for (i in 0..rope.size-2) {
                    val dx = rope[i].x - rope[i+1].x
                    val dy = rope[i].y - rope[i+1].y
                    if (abs(dx) >= 2 || abs(dy) >= 2) {
                        rope[i+1] = rope[i+1] + Point(dx.coerceIn(-1..1), dy.coerceIn(-1..1))
                    } else {
                        break
                    }
                }
                visited.add(rope.last())
            }
        }

        return visited.count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    println("Test")
    val part1 = part1(testInput)
    println(part1)
    val part2 = part2(testInput)
    println(part2)
    check(part1 == 13)
    check(part2 == 1)

    println("Waarde")
    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
