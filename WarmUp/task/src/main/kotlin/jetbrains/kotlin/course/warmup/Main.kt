package jetbrains.kotlin.course.warmup

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int, secretExample: String) =
    "Welcome to the game! $newLineSymbol" +
            newLineSymbol +
            "Two people play this game: one chooses a word (a sequence of letters), " +
            "the other guesses it. In this version, the computer chooses the word: " +
            "a sequence of $wordLength letters (for example, $secretExample). " +
            "The user has several attempts to guess it (the max number is $maxAttemptsCount). " +
            "For each attempt, the number of complete matches (letter and position) " +
            "and partial matches (letter only) is reported. $newLineSymbol" +
            newLineSymbol +
            "For example, with $secretExample as the hidden word, the BCDF guess will " +
            "give 1 full match (C) and 1 partial match (B)."

fun generateSecret(): String = "ABCD"

fun countPartialMatches(secret: String, guess: String): Int =
    countAllMatches(secret, guess) - countExactMatches(secret, guess)

fun countExactMatches(secret: String, guess: String): Int {
    var count = 0
    for ((index, symbol) in secret.withIndex()) {
        if (guess[index] == symbol) {
            count += 1
        }
    }
    return count
}

fun countAllMatches(secret: String, guess: String): Int = minOf(
    secret.filter{ it in guess }.length,
    guess.filter{ it in secret }.length
)

fun isComplete(secret: String, guess: String): Boolean = (secret == guess)

fun playGame(secret: String, wordLength: Int, maxAttemptsCount: Int): Unit {
    for (attempt in 0..maxAttemptsCount) {
        println("Please input your guess. It should be of length $wordLength.")
        val guess = safeReadLine()
        printRoundResults(secret, guess)
        if (isWon(isComplete(secret, guess), attempt, maxAttemptsCount)) {
            println("Congratulations! You guessed it!")
            return
        } else if (isLost(isComplete(secret, guess), attempt, maxAttemptsCount)) {
            println("Sorry, you lost! :( My word is $secret")
            return
        }
    }
    println("Sorry, you lost! :( My word is $secret")
}

fun printRoundResults(secret: String, guess: String): Unit {
    val fullMatches = countExactMatches(secret, guess)
    val partialMatches = countPartialMatches(secret, guess)
    println("Your guess has $fullMatches full matches and $partialMatches partial matches.")
}

fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean = (complete && attempts <= maxAttemptsCount)

fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean = (!complete && attempts > maxAttemptsCount)

fun main() {
    val wordLength = 4
    val maxAttemptsCount = 3
    val secretExample = "ACEB"

    println(getGameRules(wordLength, maxAttemptsCount, secretExample))

    playGame(generateSecret(), wordLength, maxAttemptsCount)
}
