fun main() {
    val input = getInput("day7").readLines().drop(1)
    val root = executeCommands (FileOrDir("/", true, 0, null, mutableListOf()), input).also { it.computeDirSizes() }
    println(root.fold(0) { acc, f -> if (f.isDir && f.size <= 100000) acc + f.size else acc })
    println(root.fold(root.size) { acc, f -> if (f.isDir && f.root.size - f.size <= 40000000) minOf(acc, f.size) else acc })
}

data class FileOrDir(val name: String, val isDir: Boolean, var size: Int, val parent: FileOrDir?, val contents: MutableList<FileOrDir>?) {
    val root: FileOrDir = parent?.root ?: this

    fun computeDirSizes() {
        size += (contents?.onEach { it.computeDirSizes() }?.sumOf { it.size } ?: 0)
    }

    fun <T> fold(initial: T, combine: (T, FileOrDir) -> T): T =
        contents?.fold(combine(initial, this)) { acc, f -> f.fold(acc, combine) } ?: combine(initial, this)
}

fun executeCommands(current: FileOrDir, input: List<String>): FileOrDir {
    if (input.isEmpty()){
        return current.root
    }
    val (_, first, second) = Regex("(\\$ cd |\\$ ls|dir |\\d+ )(.*)").matchEntire(input[0])!!.groupValues
    return when (first){
        "$ cd " -> executeCommands(
            when (second) {
                ".." -> current.parent!!
                "\\" -> current.root
                else -> current.contents!!.find { it.name == second } ?: FileOrDir(second, true, 0, current, mutableListOf()).also { current.contents.add(it) }
            }, input.drop(1))
        "$ ls" -> executeCommands(current, input.drop(1))
        "dir " -> executeCommands(current.also { it.contents!!.add(FileOrDir(second, true, 0, current, mutableListOf())) }, input.drop(1))
        else -> executeCommands(current.also {it.contents!!.add(FileOrDir(second, false, first.trim().toInt(), current, null)) }, input.drop(1))
    }
}
