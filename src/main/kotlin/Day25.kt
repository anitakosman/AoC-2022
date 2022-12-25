fun main() {
    val input = getInput("day25").readLines()
    println(input.sumOf { it.toSNAFUNumeral() }.toSNAFUString())
}

private fun Long.toSNAFUString(): String {
    var n = this
    var result = ""
    while (n > 0) {
        val rest = (n % 5).toInt()
        result = (if(rest > 2) rest - 5 else rest).toString() + result
        n = if (rest > 2) n / 5 + 1 else n / 5
    }
    return result.replace("-2", "=").replace("-1", "-")
}

private fun String.toSNAFUNumeral(): Long {
    return this.fold(0) { acc, c -> acc * 5 + c.toSNAFUDigit() }
}

private fun Char.toSNAFUDigit(): Int {
    return when (this) {
        '2', '1', '0' -> this.digitToInt()
        '-' -> -1
        '=' -> -2
        else -> throw IllegalArgumentException()
    }
}
