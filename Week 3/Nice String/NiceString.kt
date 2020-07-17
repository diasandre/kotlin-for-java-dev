package nicestring

val NOT_ALLOWED_SUBSTRINGS = listOf(Pair("b", "u"), Pair("b", "a"), Pair("b", "e"))
val VOWELS = listOf("a", "e", "i", "o", "u")

fun Pair<String, String>.isEqual(pair: Pair<Char, Char>) = pair.first.toString() == first && pair.second.toString() == second

fun String.isNice(): Boolean {
    val zipValues = zipWithNext()
    val containsNotAllowedSubstrings = zipValues.any { pair ->
        NOT_ALLOWED_SUBSTRINGS.any { it.isEqual(pair) }
    }

    val numberOfVowels = filter { VOWELS.contains(it.toString()) }.toList().size
    val doubleLetters = zipValues.any { it.first == it.second }

    return listOf(!containsNotAllowedSubstrings, numberOfVowels > 2, doubleLetters).filter { it }.size > 1
}