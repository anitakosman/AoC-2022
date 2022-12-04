fun main() {
    val pairsAndIntersections = getInput("day4").readLines()
        .map { Regex("(\\d+)-(\\d+),(\\d+)-(\\d+)").matchEntire(it)!!.groupValues }
        .map { IntRange(it[1].toInt(), it[2].toInt()).toSet() to IntRange(it[3].toInt(), it[4].toInt()).toSet() }
        .map { (it.first to it.second) to (it.first intersect it.second) }
    println(pairsAndIntersections.count { it.second.containsAll(it.first.first) || it.second.containsAll(it.first.second) })
    println(pairsAndIntersections.count { it.second.isNotEmpty() })
}
