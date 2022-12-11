fun main() {
    val monkeys = getInput("day11").readText().split(lineSeparator + lineSeparator).map { block ->
        val monkey = block.lines()
        Monkey(
            monkey[1].substringAfter("Starting items: ").split(",").map { it.trim().toLong() },
            convertOp(monkey[2].substringAfter("Operation: new = old ")),
            monkey[3].substringAfter("Test: divisible by ").toInt(),
            monkey[4].substringAfter("If true: throw to monkey ").toInt(),
            monkey[5].substringAfter("If false: throw to monkey ").toInt()
        )
    }
    println(monkeyBusiness(monkeys, 20) { it / 3 })
    println(monkeyBusiness(monkeys, 10000) { it.rem(monkeys.fold(1) { acc, m -> acc * m.test }) })
}

private fun monkeyBusiness(input: List<Monkey>, rounds: Int, relief: (Long) -> Long): Long {
    return (0 until input.size * rounds).fold(input) { monkeys, n ->
        val i = n % input.size
        val current = monkeys[i]
        val newWorries = current.items.map { item -> relief(current.op(item)) }
        monkeys.mapIndexed { index, monkey ->
            when (index) {
                i -> monkey.throwItems()
                current.ifTrue -> monkey.catchItems(newWorries.filter { it % current.test == 0L })
                current.ifFalse -> monkey.catchItems(newWorries.filter { it % current.test != 0L })
                else -> monkey
            }
        }
    }.sortedByDescending { it.inspectedItems }.let { it[0].inspectedItems * it[1].inspectedItems }
}

data class Monkey(val items: List<Long>, val op: (Long) -> Long, val test: Int, val ifTrue: Int, val ifFalse: Int, val inspectedItems: Long = 0) {
    fun catchItems(newItems: List<Long>) = copy(items = items + newItems)
    fun throwItems() = copy(items = emptyList(), inspectedItems = inspectedItems + items.size)
}

fun convertOp(op: String): (Long) -> Long {
    val (operator, operand) = op.split(" ")
    return fun(x): Long {
        return when (operator) {
            "+" -> when (operand) {
                "old" -> x + x
                else -> x + operand.toLong()
            }

            else -> when (operand) {
                "old" -> x * x
                else -> x * operand.toLong()
            }
        }
    }
}
