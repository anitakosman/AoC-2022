fun main() {
    val input = getInput("day10").readLines()
    println(input.fold(Triple(0, 0, 1)) { (result, cycles, x), line ->
        val (op, v) = Regex("(noop|addx) ?(-?\\d+)?").matchEntire(line)!!.groupValues.drop(1)
        when (op){
            "noop" -> Triple(if (cycles % 40 == 19) result + x * (cycles + 1) else result, cycles + 1, x)
            "addx" -> Triple(if (cycles % 40 == 19) result + x * (cycles + 1) else if (cycles % 40 == 18) result + x * (cycles + 2) else result, cycles + 2, x + v.toInt())
            else -> throw IllegalArgumentException()
        }
    }.first)
    println(input.fold(Triple("", 0, 1)) { (result, cycles, x), line ->
        val (op, v) = Regex("(noop|addx) ?(-?\\d+)?").matchEntire(line)!!.groupValues.drop(1)
        when (op){
            "noop" -> Triple(result + if ((cycles) % 40 in (x - 1..x + 1)) "#" else ".", cycles + 1, x)
            "addx" -> Triple(result + (if ((cycles) % 40 in (x - 1..x + 1)) "#" else ".") + (if ((cycles + 1) % 40 in (x - 1..x + 1)) "#" else "."), cycles + 2, x + v.toInt())
            else -> throw IllegalArgumentException()
        }
    }.first.chunked(40).joinToString(lineSeparator))
}