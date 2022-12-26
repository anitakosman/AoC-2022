fun main() {
    val input = getInput("day13")
    input.readText()
        .split(lineSeparator + lineSeparator)
        .map { it.lines().let { lines -> lines[0] to lines[1] } }
        .withIndex().filter { compare(it.value.first, it.value.second) <= 0 }.sumOf { it.index + 1 }
        .also(::println)

    (input.readLines().filter { it.isNotEmpty() } + "[[2]]" + "[[6]]")
        .sortedWith(::compare)
        .let { (it.indexOf("[[2]]") + 1) * (it.indexOf("[[6]]") + 1) }
        .also(::println)
}

fun compare(left: String, right: String): Int {
    if (left.isEmpty()) {
        return -1
    }

    if (right.isEmpty()) {
        return 1
    }

    if (left[0].isDigit()) {
        if (right[0].isDigit()) {
            val l = left.takeWhile { it.isDigit() }.toInt()
            val r = right.takeWhile { it.isDigit() }.toInt()
            if (l == r) {
                return compare(left.dropWhile { it.isDigit() }, right.dropWhile { it.isDigit() })
            }
            return l.compareTo(r)
        } else {
            return compare("[${left.takeWhile { it.isDigit() }}]${left.dropWhile { it.isDigit() }}", right)
        }
    } else if (right[0].isDigit()) {
        return compare(left, "[${right.takeWhile { it.isDigit() }}]${right.dropWhile { it.isDigit() }}")
    } else if (left[0] == right[0]) {
        return compare(left.drop(1), right.drop(1))
    } else {
        return if (left[0] == ']') -1 else 1
    }
}
