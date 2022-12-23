fun main() {
    val (input, path) = getInput("day22").readText().split(lineSeparator + lineSeparator)
    val map = input.lines()
    val spaces = (map.indices)
        .flatMap { y -> (0 until map[0].length).map { x -> x to y } }
        .filter { (x, y) -> map[y].getOrElse(x){ ' ' } != ' ' }.toSet()
    val walls = spaces.filter { (x, y) -> map[y][x] == '#' }.toSet()

    val (pos, facing) = followPath(path, spaces, walls)
    println(1000 * (pos.second + 1) + 4 * (pos.first + 1) + facing.ordinal)
}

fun followPath(input: String, spaces: Set<Pair<Int, Int>>, walls: Set<Pair<Int, Int>>): Pair<Pair<Int, Int>, Facing> {
    var path = input
    var pos = (spaces - walls).filter { it.second == 0 }.minBy { it.first }
    var facing = Facing.EAST

    while (path.isNotEmpty()) {
        when (path[0]) {
            'L' -> {
                facing = facing.turnLeft()
                path = path.drop(1)
            }

            'R' -> {
                facing = facing.turnRight()
                path = path.drop(1)
            }

            else -> {
                val steps = path.takeWhile { it.isDigit() }.toInt()
                repeat(steps) {
                    var p = facing.move(pos)
                    if (p !in spaces) {
                        p = when (facing) {
                            Facing.EAST -> spaces.filter { it.second == p.second }.minBy { it.first }
                            Facing.SOUTH -> spaces.filter { it.first == p.first }.minBy { it.second }
                            Facing.WEST -> spaces.filter { it.second == p.second }.maxBy { it.first }
                            Facing.NORTH -> spaces.filter { it.first == p.first }.maxBy { it.second }
                        }
                    }
                    if (p !in walls) {
                        pos = p
                    }
                }
                path = path.dropWhile { it.isDigit() }
            }
        }
    }

    return pos to facing
}