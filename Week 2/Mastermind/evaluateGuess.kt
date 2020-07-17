package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

private fun String.toMap() = this.mapIndexed { index, char ->
    index to char
}.toMap()

private fun removeRepeatedLetters(allowedChars: MutableList<Char>, list: MutableList<Char>) {
    val findAllowedRepeatedLetters = allowedChars.groupBy { it }
    list.groupBy { it }.forEach { (char, values) ->
        val sizeSecret = findAllowedRepeatedLetters[char]?.size ?: 0
        val sizeResponse = values.size
        if (sizeSecret != sizeResponse) {
            list.removeAll(values)
            val to = when {
                sizeResponse > sizeSecret -> {
                    sizeSecret
                }
                sizeSecret > sizeResponse -> {
                    sizeResponse
                }
                else -> {
                    sizeSecret - sizeResponse
                }
            }

            for (x in 0 until to) {
                list.add(values.first())
            }
        }
        values.forEach { allowedChars.remove(it) }
    }
}

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val rightPosition = mutableListOf<Char>()
    val wrongPosition = mutableListOf<Char>()

    val allowedChars = secret.toList()

    val secretMappedValues = secret.toMap()
    val guessMappedValues = guess.toMap()

    guessMappedValues.forEach { (key, value) ->
        secretMappedValues[key]?.let {
            if (allowedChars.contains(value)) {
                if (it == value) rightPosition.add(value) else wrongPosition.add(value)
            }
        }
    }

    val toMutableList = allowedChars.toMutableList()

    removeRepeatedLetters(toMutableList, rightPosition)
    removeRepeatedLetters(toMutableList, wrongPosition)

    return Evaluation(rightPosition.size, wrongPosition.size)
}
