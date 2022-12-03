import java.io.File

fun getInput(fileName: String): File {
    return File(object{}.javaClass.getResource("/${fileName}.txt")!!.file)
}

val lineSeparator: String = System.lineSeparator()