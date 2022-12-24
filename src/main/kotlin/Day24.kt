fun main() {
    val input = getInput("day24").readLines().drop(1).dropLast(1).map { it.drop(1).dropLast(1) }
    val m = input[0].length
    val n = input.size
    val blizzards = input.flatMapIndexed { y, line ->
        line.withIndex()
            .filter { it.value in setOf('>', '<', '^', 'v') }
            .map { (x, c) -> Blizzard(x, y, c, m, n) }
    }

    val t1 = findPath(m, n, blizzards, 0 to -1, (m - 1) to n)
    println(t1)
    val t2 = findPath(m, n, blizzards, (m - 1) to n, 0 to -1, t1)
    val t3 = findPath(m, n, blizzards, 0 to -1, (m - 1) to n, t2)
    println(t3)
}

private fun findPath(m: Int, n: Int, blizzards: List<Blizzard>, startPos: Pair<Int, Int>, endPos: Pair<Int, Int>, startT: Int = 0): Int {
    var t = startT
    var positions = setOf(startPos)
    while (endPos !in positions) {
        t++
        positions = positions.flatMap { it.movesInGrid(m, n) }.toSet() - blizzards.map { it.position(t) }.toSet()
    }
    return t
}

fun Pair<Int, Int>.movesInGrid(m: Int, n: Int) =
    listOf((-1 to 0), (0 to 0), (1 to 0), (0 to -1), (0 to 1))
        .map { (this.first + it.first) to (this.second + it.second) }
        .filter { (it.first in (0 until m) && it.second in (0 until n)) || it == (0 to -1) || it == ((m - 1) to n) }

class Blizzard(x: Int, y: Int, c: Char, m: Int, n: Int) {
    private val formula: (Int) -> Pair<Int, Int>

    init {
        formula = when (c) {
            'v' -> { t -> x to ((y + t) % n) }
            '^' -> { t -> x to ((y - (t % n) + n) % n) }
            '>' -> { t -> ((x + t) % m) to y }
            '<' -> { t -> ((x - (t % m) + m) % m) to y }
            else -> throw IllegalArgumentException()
        }
    }

    fun position(t: Int) = formula(t)
}