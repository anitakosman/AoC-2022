fun main() {
    val battles = getInput("day2").readLines().map { it[0] - 'A' to it[2] - 'X' }
    println(battles.sumOf { 3 * ((it.second - it.first + 4) % 3) + it.second + 1 })
    println(battles.sumOf { 3 * it.second + ((it.first + it.second + 2) % 3) + 1 })
}