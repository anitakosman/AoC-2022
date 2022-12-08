fun main() {
    val input = getInput("day5").readText().split(lineSeparator + lineSeparator).map { it.lines() }
    val numberOfCrates = input[0].last().split(" ").filter { it.isNotBlank() }.maxOf { it.toInt() }
    val stacks =
        input[0].dropLast(1).fold(List(numberOfCrates) { _ -> "" }) { acc, line ->
            line.windowed(3, 4).withIndex().map { (index, crate) -> acc[index] + crate[1] }
        }.map { it.trim() }

    println(followInstructions(input, stacks, true).joinToString("") { it.getOrNull(0)?.toString() ?: "" })
    println(followInstructions(input, stacks, false).joinToString("") { it.getOrNull(0)?.toString() ?: "" })
}

private fun followInstructions(input: List<List<String>>, stacks: List<String>, reverse: Boolean): List<String> {
    return input[1].fold(stacks) { acc, line ->
        val (amount, from, to) = Regex("move (\\d+) from (\\d+) to (\\d+)").matchEntire(line)!!.groupValues.drop(1)
            .map { it.toInt() }
        acc.mapIndexed { i, stack ->
            when (i) {
                from - 1 -> stack.drop(amount)
                to - 1 -> acc[from - 1].take(amount).let { if (reverse) it.reversed() else it} + stack
                else -> stack
            }
        }
    }
}
