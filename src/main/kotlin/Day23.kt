fun main() {
    val input = getInput("day23").readLines()

    var elves = input.indices
        .flatMap { y -> input[0].indices.map { it to y } }
        .filter { input[it.second][it.first] == '#' }

    var directions = listOf(Facing.NORTH, Facing.SOUTH, Facing.WEST, Facing.EAST)
    var movingElves = getMoving(elves)
    var round = 0
    while (movingElves.isNotEmpty()){
        if (round == 10){
            println((elves.maxOf { it.first } - elves.minOf { it.first } + 1) * (elves.maxOf { it.second } - elves.minOf { it.second } + 1) - elves.size)
        }

        val considerations = movingElves.map { elf -> elf to (
            directions.find { direction -> direction.look(elf).none { it in elves } }?.move(elf) ?: elf
        ) }
        val moves = considerations.groupBy { it.second }.filter { it.value.size == 1 }.map { it.value.first() }

        elves = (elves - moves.map { it.first }.toSet() + moves.map { it.second })
        directions = directions.drop(1) + directions[0]
        movingElves = getMoving(elves)
        round++
    }

    println(round + 1)
}

private fun getMoving(elves: List<Pair<Int, Int>>) = elves.filter {
    (-1..1).any { dx -> (-1..1).any { dy -> (dx != 0 || dy != 0) && ((it.first + dx) to (it.second + dy) in elves) } }
}