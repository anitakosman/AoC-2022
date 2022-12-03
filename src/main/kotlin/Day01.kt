fun main() {
    val elves = getInput("day1").readText()
        .split(lineSeparator + lineSeparator)
        .map { elf -> elf.split(lineSeparator).sumOf { it.toInt() } }
        .sortedDescending()
    println(elves.first())
    println(elves.take(3).sum())
}