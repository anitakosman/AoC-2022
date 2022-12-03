fun main() {
    val rucksacks = getInput("day3").readLines()
    println(rucksacks
        .map { it.chunked(it.length / 2) }
        .sumOf { it[0].toSet().intersect(it[1].toSet()).first().priority() }
    )
    println(rucksacks
        .chunked(3)
        .sumOf { it[0].toSet().intersect(it[1].toSet()).intersect(it[2].toSet()).first().priority() }
    )
}

private fun Char.priority() = if (this.isUpperCase()) this - 'A' + 27 else this - 'a' + 1