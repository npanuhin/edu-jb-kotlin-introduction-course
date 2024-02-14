package jetbrains.kotlin.course.hangman

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int) = "Welcome to the game!$newLineSymbol$newLineSymbol" +
        "In this game, you need to guess the word made by the computer.$newLineSymbol" +
        "The hidden word will appear as a sequence of underscores, one underscore means one letter.$newLineSymbol" +
        "You have $maxAttemptsCount attempts to guess the word.$newLineSymbol" +
        "All words are English words, consisting of $wordLength letters.$newLineSymbol" +
        "Each attempt you should enter any one letter,$newLineSymbol" +
        "if it is in the hidden word, all matches will be guessed.$newLineSymbol$newLineSymbol" +
        "" +
        "For example, if the word \"CAT\" was guessed, \"_ _ _\" will be displayed first, " +
        "since the word has 3 letters.$newLineSymbol" +
        "If you enter the letter A, you will see \"_ A _\" and so on.$newLineSymbol$newLineSymbol" +
        "" +
        "Good luck in the game!"

// You will use this function later
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = complete && attempts <= maxAttemptsCount

// You will use this function later
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = !complete && attempts > maxAttemptsCount

fun isComplete(secret: String, currentGuess: String): Boolean = (secret == currentGuess.replace(separator, ""))

fun generateNewUserWord(secret: String, guess: Char, currentUserWord: String): String {
    val newWord = StringBuilder()
    var secretIndex = 0
    for (i in currentUserWord.indices) {
        if (currentUserWord[i] == ' ') continue

        if (currentUserWord[i] != '_') {
            newWord.append(currentUserWord[i])
        } else if (secret[secretIndex] == guess) {
            newWord.append(guess)
        } else {
            newWord.append('_')
        }
        newWord.append(separator)
        ++secretIndex
    }
    return newWord.toString().removeSuffix(separator)
}

fun getRoundResults(secret: String, guess: Char, currentUserWord: String): String {
    val newUserWord = generateNewUserWord(secret, guess, currentUserWord)
    if (guess in secret) {
        println("Great! This letter is in the word! The current word is $newUserWord")
    } else {
        println("Sorry, the secret does not contain the symbol: $guess. The current word is $currentUserWord")
    }
    return newUserWord
}

fun generateSecret(): String = words.random()

fun getHiddenSecret(wordLength: Int): String = (underscore + separator).repeat(wordLength).trim()

fun isCorrectInput(userInput: String): Boolean {
    if (userInput.length != 1) {
        println("The length of your guess should be 1! Try again!")
        return false
    }
    if (!userInput[0].isLetter()) {
        println("You should input only English letters! Try again!")
        return false
    }
    return true
}

fun safeUserInput(): Char {
    println("Please input your guess.")
    var guess: String
    do{
        guess = safeReadLine()
    } while (!isCorrectInput(guess))
    return guess[0].uppercaseChar()
}

fun playGame(secret: String, maxAttemptsCount: Int): Unit {
    var currentUserWord = getHiddenSecret(secret.length)
    var attempts = 0
    while (attempts < maxAttemptsCount) {
        println("I guessed a: $currentUserWord")
        println("Please input your guess.")
        val guess = safeUserInput()
        currentUserWord = getRoundResults(secret, guess, currentUserWord)
        if (isComplete(secret, currentUserWord)) {
            println("Congratulations! You guessed it!")
            break
        }
        ++attempts
    }
    println("Sorry, you lost! My word is $secret")
}

fun main() {
    // Uncomment this code on the last step of the game

     println(getGameRules(wordLength, maxAttemptsCount))
     playGame(generateSecret(), maxAttemptsCount)
}
