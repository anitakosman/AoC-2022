fun main() {
    val input = getInput("day12").readLines()
    val grid = input.map { line -> line.map { if (it == 'S') 0 else if (it == 'E') 25 else it - 'a' } }

    val startY = input.indexOfFirst { it.contains('S') }
    val start = startY to input[startY].indexOf('S')

    val endY = input.indexOfFirst { it.contains('E') }
    val end = endY to input[endY].indexOf('E')

    val lowestPoints = input.flatMapIndexed { y, s -> s.withIndex().filter { it.value == 'a' }.map { y to it.index } }

    println(findPath(grid, start, end))
    println(lowestPoints.minOf { findPath(grid, it, end) })
}

fun findPath(grid: List<List<Int>>, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    var current = setOf(start)
    var steps = 0
    while (!current.contains(end)){
        if (current.isEmpty()) {
            return Int.MAX_VALUE
        }
        visited.addAll(current)
        current = current.map { availablePos(grid, it) }.reduce(Set<Pair<Int, Int>>::plus) - visited
        steps++
    }
    return steps
}

fun availablePos(grid: List<List<Int>>, pos: Pair<Int, Int>): Set<Pair<Int, Int>> {
    val currentHeight = grid[pos.first][pos.second]
    return listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
        .map { pos.first + it.first to pos.second + it.second }
        .filter { (grid.getOrNull(it.first)?.getOrNull(it.second)?.minus(currentHeight) ?: 2) < 2 }
        .toSet()
}
