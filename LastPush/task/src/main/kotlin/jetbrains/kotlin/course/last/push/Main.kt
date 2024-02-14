package jetbrains.kotlin.course.last.push

// You will use this function later
fun getPattern(): String {
    println(
        "Do you want to use a pre-defined pattern or a custom one? " +
                "Please input 'yes' for a pre-defined pattern or 'no' for a custom one"
    )
    do {
        when (safeReadLine()) {
            "yes" -> {
                return choosePattern()
            }
            "no" -> {
                println("Please, input a custom picture")
                return safeReadLine()
            }
            else -> println("Please input 'yes' or 'no'")
        }
    } while (true)
}

// You will use this function later
fun choosePattern(): String {
    do {
        println("Please choose a pattern. The possible options: ${allPatterns().joinToString(", ")}")
        val name = safeReadLine()
        val pattern = getPatternByName(name)
        pattern?.let {
            return@choosePattern pattern
        }
    } while (true)
}

// You will use this function later
fun chooseGenerator(): String {
    var toContinue = true
    var generator = ""
    println("Please choose the generator: 'canvas' or 'canvasGaps'.")
    do {
        when (val input = safeReadLine()) {
            "canvas", "canvasGaps" -> {
                toContinue = false
                generator = input
            }
            else -> println("Please, input 'canvas' or 'canvasGaps'")
        }
    } while (toContinue)
    return generator
}

// You will use this function later
fun safeReadLine(): String = readlnOrNull() ?: error("Your input is incorrect, sorry")

fun fillPatternRow(patternRow: String, patternWidth: Int) = if (patternRow.length <= patternWidth) {
    val filledSpace = "$separator".repeat(patternWidth - patternRow.length)
    "$patternRow$filledSpace"
} else {
    error("patternRow length > patternWidth, please check the input!")
}

fun getPatternHeight(pattern: String) = pattern.lines().size

fun repeatHorizontally(pattern: String, n: Int, patternWidth: Int): String {
    val pictureRows = pattern.lines()
    val sb = StringBuilder()
    for (row in pictureRows) {
        val currentRow = fillPatternRow(row, patternWidth)
        sb.append(currentRow.repeat(n))
        sb.append("\n")
    }
    return sb.toString().trimEnd('\n')
}

fun dropTopFromLine(line: String, width: Int, patternHeight: Int, patternWidth: Int): String =
    if (patternHeight > 1) {
        line.lines().drop(1).joinToString("\n")
    } else {
        line
    }

fun canvasGenerator(pattern: String, width: Int, height: Int): String {
    val result = StringBuilder()
    for (line in repeatHorizontally(pattern, width, pattern.lines().maxOfOrNull { it.length } ?: 0)) {
        result.append(line)
    }
    val newPattern = dropTopFromLine(pattern, width, pattern.lines().size, pattern.length)
    for (i in 1 until height) {
        result.append(newLineSymbol)
        for (line in repeatHorizontally(newPattern, width, pattern.lines().maxOfOrNull { it.length } ?: 0)) {
            result.append(line)
        }
    }
    return result.toString()
}

fun canvasWithGapsGenerator(pattern: String, width: Int, height: Int): String {
    val result = StringBuilder()
    val patternWidth = (pattern.lines().maxOfOrNull { it.length } ?: 0)
    for (i in 0 until height) {
        if (i % 2 == 0) {
            for (line in repeatHorizontally(pattern, (width + 1) / 2, 2 * patternWidth).lines()) {
                var newLine = line
                if (width % 2 == 1) {
                    newLine = newLine.dropLast(patternWidth)
                }
                result.append(newLine)
                result.append(newLineSymbol)
            }
        } else {
            for (line in repeatHorizontally(pattern, maxOf(1, width / 2), 2 * patternWidth).lines()) {
                if (width > 1) {
                    result.append(separator.toString().repeat(patternWidth))
                }

                var newLine = line
                if (width == 1 || width % 2 == 0) {
                    newLine = newLine.dropLast(patternWidth)
                }
                result.append(newLine)
                result.append(newLineSymbol)
            }
        }
    }
    return result.toString()
}

fun applyGenerator(pattern: String, generatorName: String, width: Int, height: Int): String =
    when (generatorName) {
        "canvas" -> {
            val result = canvasGenerator(pattern, width, height).lines().joinToString(newLineSymbol);
            if (!result.endsWith(newLineSymbol)) {
                result + newLineSymbol
            } else {
                result
            }
        }
        "canvasGaps" -> {
            val result = canvasWithGapsGenerator(pattern, width, height).lines().joinToString(newLineSymbol)
            if (!result.endsWith(newLineSymbol)) {
                result + newLineSymbol
            } else {
                result
            }
        }
        else -> error("Unknown generator name")
    }

fun main() {
     val pattern = getPattern()
     val generatorName = chooseGenerator()
     println("Please input the width of the resulting picture:")
     val width = safeReadLine().toInt()
     println("Please input the height of the resulting picture:")
     val height = safeReadLine().toInt()

     println("The pattern:$newLineSymbol${pattern.trimIndent()}")

     println("The generated image:")
     println(applyGenerator(pattern, generatorName, width, height))
}
