fun main() {
    val input = getInput("day6").readText()
    println(input.windowed(4).map { it.toList() }.withIndex().first { it.value.distinct() == it.value }.index + 4)
    println(input.windowed(14).map { it.toList() }.withIndex().first { it.value.distinct() == it.value }.index + 14)
}
