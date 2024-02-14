package jetbrains.kotlin.course.almost.done


fun trimPicture(picture: String): String = picture.trimIndent()

fun applyBordersFilter(picture: String): String {
    val width = getPictureWidth(picture) + 4
    val result = StringBuilder()

    result.append(borderSymbol.toString().repeat(width))
    result.append(newLineSymbol)
    for (line in picture.lines()) {
        val curWidth = line.length
        result.append(borderSymbol)
        result.append(separator)
        result.append(line)
        result.append(separator.toString().repeat(width - 3 - curWidth))
        result.append(borderSymbol)
        result.append(newLineSymbol)
    }
    result.append(borderSymbol.toString().repeat(width))
    return result.toString()
}

fun applySquaredFilter(picture: String): String {
    val borderedPicture = applyBordersFilter(picture)
    val result = StringBuilder()
    for (line in borderedPicture.lines()) {
        result.append(line)
        result.append(line)
        result.append(newLineSymbol)
    }
    for (line in borderedPicture.lines().drop(1)) {
        result.append(line)
        result.append(line)
        result.append(newLineSymbol)
    }
    return result.toString()
}

fun applyFilter(picture: String, filter: String): String {
    val result = trimPicture(picture)
    return when (filter) {
        "borders" -> applyBordersFilter(result)
        "squared" -> applySquaredFilter(result)
        else -> error("Unknown filter")
    }
}

fun safeReadLine(): String =  when (val input = readlnOrNull()) {
    null -> error("No input")
    else -> input
}

fun chooseFilter(): String {
    var input: String
    do {
        println("Please input 'borders' or 'squared'")
        input = safeReadLine()
    } while (input != "borders" && input != "squared")
    return input;
}

fun choosePicture(): String {
    var input: String
    do {
        println("Please choose a picture. The possible options are: ${allPictures()}")
        input = safeReadLine()
    } while (getPictureByName(input) == null)
    return getPictureByName(input)!!
}

fun getPicture(): String {
    println("Do you want to use a predefined picture or a custom one? Please input 'yes' for a predefined image or 'no' for a custom one")
    do {
        val input = safeReadLine()
        when (input) {
            "yes" -> return choosePicture()
            "no" -> {
                println("Please input a custom picture")
                return safeReadLine()
            }
        }
    } while (true)
}

fun photoshop(): Unit  {
    val picture = getPicture()
    val filter = chooseFilter()
    println("The old image:")
    println(picture)
    println("The transformed picture:")
    println(applyFilter(picture, filter))
}

fun main() {
    photoshop()
}
