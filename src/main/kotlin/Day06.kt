fun main() {
    val input = getInput("day6").readText()
    println(input.windowed(4).map { it.toList() }.indexOfFirst { it.distinct() == it } + 4)
    println(input.windowed(14).map { it.toList() }.indexOfFirst { it.distinct() == it } + 14)
}
