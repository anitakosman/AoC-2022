import kotlin.math.abs

fun main() {
    val input = getInput("day9").readLines()
    println(moveRopes(MutableList(2) { _ -> 0 to 0 }, mutableSetOf(), input).size)
    println(moveRopes(MutableList(10) { _ -> 0 to 0 }, mutableSetOf(), input).size)
}

fun moveRopes(knots: MutableList<Pair<Int, Int>>, visited: MutableSet<Pair<Int, Int>>, input: List<String>): MutableSet<Pair<Int, Int>> {
    if (input.isEmpty()){
        return visited
    }
    val (direction, steps) = Regex("([UDLR]) (\\d+)").matchEntire(input[0])!!.groupValues.drop(1)
    moveSteps(direction, steps.toInt(), knots, visited)
    return moveRopes(knots, visited, input.drop(1))
}

fun moveSteps(direction: String, steps: Int, knots: MutableList<Pair<Int, Int>>, visited: MutableSet<Pair<Int, Int>>) {
    if (steps == 0) {
        return
    }
    moveHead(knots, direction)
    moveRest(knots)
    visited.add(knots.last())
    moveSteps(direction, steps - 1, knots, visited)
}

private fun moveHead(
    knots: MutableList<Pair<Int, Int>>,
    direction: String
) {
    knots[0] = when (direction) {
        "U" -> knots[0].first to knots[0].second + 1
        "D" -> knots[0].first to knots[0].second - 1
        "R" -> knots[0].first + 1 to knots[0].second
        "L" -> knots[0].first - 1 to knots[0].second
        else -> throw IllegalArgumentException()
    }
}

private fun moveRest(knots: MutableList<Pair<Int, Int>>) {
    (1 until knots.size).forEach {
        val dx = knots[it - 1].first - knots[it].first
        val dy = knots[it - 1].second - knots[it].second
        knots[it] = if (abs(dx) > 1 || abs(dy) > 1) {
            knots[it].first + sign(dx) to knots[it].second + sign(dy)
        } else knots[it]
    }
}

fun sign(x: Int) = if (x == 0) 0 else if (x > 0) 1 else -1
