class Solution {
    fun foreignDictionary(words: Array<String>): String {
        val adjacencyList = HashMap<Char, HashSet<Char>>()

        for (word in words) {
            for (character in word) {
                adjacencyList.putIfAbsent(character, hashSetOf())
            }
        }

        for (wordIndex in 0 until words.size - 1) {
            val currentWord = words[wordIndex]
            val nextWord = words[wordIndex + 1]
            val minimumLength = minOf(currentWord.length, nextWord.length)

            if (
                currentWord.length > nextWord.length &&
                currentWord.substring(0, minimumLength) ==
                nextWord.substring(0, minimumLength)
            ) return ""

            for (characterIndex in 0 until minimumLength) {
                if (currentWord[characterIndex] != nextWord[characterIndex]) {
                    adjacencyList[currentWord[characterIndex]]
                        ?.add(nextWord[characterIndex])
                    break
                }
            }
        }

        val visitStateByCharacter = HashMap<Char, Int>()
        val topologicalOrderReversed = mutableListOf<Char>()

        fun detectCycleAndBuildOrderDFS(currentCharacter: Char): Boolean {
            if (currentCharacter in visitStateByCharacter) {
                return visitStateByCharacter[currentCharacter] == 1
            }

            visitStateByCharacter[currentCharacter] = 1

            for (neighborCharacter in adjacencyList[currentCharacter] ?: emptySet()) {
                if (detectCycleAndBuildOrderDFS(neighborCharacter)) {
                    return true
                }
            }

            visitStateByCharacter[currentCharacter] = -1
            topologicalOrderReversed.add(currentCharacter)

            return false
        }

        for (character in adjacencyList.keys) {
            if (detectCycleAndBuildOrderDFS(character)) return ""
        }

        return topologicalOrderReversed.reversed().joinToString("")
    }
}
