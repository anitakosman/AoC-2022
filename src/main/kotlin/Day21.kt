fun main() {
    val input = getInput("day21").readLines().map { it.substringBefore(": ") to it.substringAfter(": ") }
    val digits = input.filter { it.second.all(Char::isDigit) }.associate { it.first to it.second.toLong() }
    val ops = input.filter { it.second.any { c -> !c.isDigit() } }
        .associate { it.first to it.second.toComputingMonkey(it.first) }

    val computed = compute(digits, ops, "root")
    println(computed["root"])

    val leftRoot = ops["root"]!!.left
    val rightRoot = ops["root"]!!.right
    val correctlyComputed = compute(digits.minus("humn"), ops.minus("root"), leftRoot, rightRoot)
    if (correctlyComputed.containsKey(leftRoot)) {
        correctlyComputed[rightRoot] = correctlyComputed[leftRoot]!!
    } else {
        correctlyComputed[leftRoot] = correctlyComputed[rightRoot]!!
    }
    solve(correctlyComputed, ops)
    println(correctlyComputed["humn"])
}

private fun compute(digits: Map<String, Long>, ops: Map<String, ComputingMonkey>, vararg ends: String): MutableMap<String, Long> {
    val computed = digits.toMutableMap()
    while (ends.none { it in computed.keys }) {
        ops.entries.filter { it.key !in computed.keys }.forEach {
            val result = it.value.apply(computed)
            if (result != null) {
                computed[it.key] = result
            }
        }
    }
    return computed
}

private fun solve(correctlyComputed: MutableMap<String, Long>, ops: Map<String, ComputingMonkey>) {
    while ("humn" !in correctlyComputed.keys) {
        ops.entries.filter { it.key in correctlyComputed.keys && it.value.left !in correctlyComputed.keys }
            .forEach { (_, monkey) ->
                val result = monkey.applyReverseWithRight(correctlyComputed)
                if (result != null) {
                    correctlyComputed[monkey.left] = result
                }
            }
        ops.entries.filter { it.key in correctlyComputed.keys && it.value.right !in correctlyComputed.keys }
            .forEach { (_, monkey) ->
                val result = monkey.applyReverseWithLeft(correctlyComputed)
                if (result != null) {
                    correctlyComputed[monkey.right] = result
                }
            }
        ops.entries.filter { it.key !in correctlyComputed.keys }.forEach {
            val result = it.value.apply(correctlyComputed)
            if (result != null) {
                correctlyComputed[it.key] = result
            }
        }
    }
}

data class ComputingMonkey(
    val name: String,
    val left: String,
    val right: String,
    val op: (Long, Long) -> Long,
    val reverseLeft: (Long, Long) -> Long,
    val reverseRight: (Long, Long) -> Long
) {
    fun apply(m: Map<String, Long>) = m[left]?.let { l -> m[right]?.let { r -> op(l, r) } }
    fun applyReverseWithLeft(m: Map<String, Long>) = m[name]?.let { n -> m[left]?.let { l -> reverseLeft(n, l) } }
    fun applyReverseWithRight(m: Map<String, Long>) = m[name]?.let { n -> m[right]?.let { r -> reverseRight(n, r) } }
}

fun String.toComputingMonkey(name: String): ComputingMonkey {
    val (left, o, right) = this.split(" ")
    val op: (Long, Long) -> Long = when (o) {
        "+" -> Long::plus
        "-" -> Long::minus
        "*" -> Long::times
        "/" -> Long::div
        else -> throw UnsupportedOperationException()
    }
    val reverseLeft: (Long, Long) -> Long = when (o) {
        "+" -> Long::minus
        "-" -> { a, b -> b - a }
        "*" -> Long::div
        "/" -> { a, b -> b / a }
        else -> throw UnsupportedOperationException()
    }
    val reverseRight: (Long, Long) -> Long = when (o) {
        "+" -> Long::minus
        "-" -> Long::plus
        "*" -> Long::div
        "/" -> Long::times
        else -> throw UnsupportedOperationException()
    }
    return ComputingMonkey(name, left, right, op, reverseLeft, reverseRight)
}
