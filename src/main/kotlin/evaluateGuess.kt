package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rightPosition = 0
    var wrongPosition = 0

    // Builds a map for secret with only letters in common between guess and secret and stores its
    // positions for each of these letters
    val secretMap = getLetterPositionMap(guess, secret)
    // Builds a map for guess with only letters in common between guess and secret and stores its
    // positions for each of these letters
    val guessMap = getLetterPositionMap(secret, guess)

    for (guessKey in guessMap.keys) {
        // Get positions for this key in guessMap
        val guessValues = guessMap[guessKey]
        if (secretMap.containsKey(guessKey)) {
            // Get positions for this key in secretMap
            val secretValues = secretMap[guessKey]
            // Get the right positions for the values of the current guessKey
            val right = getRightPositions(guessValues, secretValues)
            if (right > 0) {
                rightPosition += right
                wrongPosition += if (secretValues!!.size < guessValues!!.size) {
                    secretValues.size - right
                } else {
                    guessValues.size - right
                }
            } else {
                wrongPosition += if (secretValues!!.size < guessValues!!.size) {
                    secretValues.size
                } else {
                    guessValues.size
                }
            }
        }
    }

    return Evaluation(rightPosition, wrongPosition)
}

fun getLetterPositionMap(word1: String, word2: String) : HashMap<Char, List<Int>> {
    val letterPositionMap = HashMap<Char, List<Int>>()
    val letterList = ArrayList<Char>()
    for (gch in word1) {
        if (word2.contains(gch)) {
            if (!letterList.contains(gch)) {
                letterList.add(gch)
            }

            val posList = ArrayList<Int>()
            for (i in word2.indices) {
                if (word2[i] == gch) {
                    posList.add(i)
                }
            }
            letterPositionMap[gch] = posList
        }
    }
    return letterPositionMap
}

fun getRightPositions(guessValues: List<Int>?, secretValues: List<Int>?) : Int {
    var right = 0;
    if (guessValues != null && secretValues != null) {
        for (gv in guessValues) {
            if (secretValues.contains(gv)) {
                right++
            }
        }
    }
    return right
}
