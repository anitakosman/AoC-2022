import java.io.File

fun getInput(fileName: String): File {
    return File(object{}.javaClass.getResource("/${fileName}.txt")!!.file)
}

val lineSeparator: String = System.lineSeparator()

enum class Facing(private val dx: Int, private val dy: Int, private val diagonals: List<Pair<Int, Int>>) {
    EAST(1, 0, listOf(1 to -1, 1 to 1)),
    SOUTH(0, 1, listOf(-1 to 1, 1 to 1)),
    WEST(-1, 0, listOf(-1 to -1, -1 to 1)),
    NORTH(0, -1, listOf(-1 to -1, 1 to -1));

    fun move(pos: Pair<Int, Int>) = (pos.first + dx) to (pos.second + dy)

    fun look(pos: Pair<Int, Int>) = (diagonals + (dx to dy)).map { pos.first + it.first to pos.second + it.second }

    fun turnLeft() = when (this) {
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
        NORTH -> WEST
    }

    fun turnRight() = when (this) {
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
        NORTH -> EAST
    }
}