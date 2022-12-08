import java.util.function.Predicate

fun main() {
    val trees = getInput("day8").readLines().map { line ->
        line.map { it.toString().toInt() }
    }
    println(trees.withIndex().sumOf { (y, line) ->
        line.withIndex().filter { (x, tree) ->
            x == 0 || y == 0
                    || (line.take(x).maxOrNull()?.compareTo(tree) ?: -1) < 0
                    || (line.drop(x + 1).maxOrNull()?.compareTo(tree) ?: -1) < 0
                    || (trees.take(y).maxOfOrNull { it[x] }?.compareTo(tree) ?: -1) < 0
                    || (trees.drop(y + 1).maxOfOrNull { it[x] }?.compareTo(tree) ?: -1) < 0
        }.size
    })
    println(trees.withIndex().maxOf { (y, line) ->
        line.withIndex().maxOf { (x, tree) ->
            line.take(x).reversed().takeUntillInclude { it >= tree }.size *
                    line.drop(x + 1).takeUntillInclude { it >= tree }.size *
                    trees.take(y).map { it[x] }.reversed().takeUntillInclude { it >= tree }.size *
                    trees.drop(y + 1).map { it[x] }.takeUntillInclude { it >= tree }.size
        }
    })
}

fun <T> List<T>.takeUntillInclude(predicate: Predicate<T>) : List<T> {
    var conditionMet = false
    return this.fold(emptyList()){ acc: List<T>, t: T ->
        if (conditionMet) {
            acc
        } else if (predicate.test(t)) {
            conditionMet = true
            acc + t
        } else {
            acc + t
        }
    }
}
