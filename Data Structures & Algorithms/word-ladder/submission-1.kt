class Solution {
    fun ladderLength(
        beginWord: String, 
        endWord: String, 
        wordList: List<String>
    ): Int {

     if (!wordList.contains(endWord) || beginWord == endWord) return 0

     val availableWords = wordList.toMutableSet()
     var transformationLength = 0
     val bfsQueue = ArrayDeque<String>().apply {
        add(beginWord)
     }

     while (bfsQueue.isNotEmpty()) {
        transformationLength++

        repeat(bfsQueue.size) {
            val currentWord = bfsQueue.removeFirst()

            if (currentWord == endWord) return transformationLength

            for (characterIndex in currentWord.indices) {
                for (replacementCharacter in 'a'..'z') {
                    if (currentWord[characterIndex] == replacementCharacter) continue

                    val neighborWord =
                        currentWord.substring(0, characterIndex) +
                        replacementCharacter +
                        currentWord.substring(characterIndex + 1)

                    if (availableWords.remove(neighborWord)) {
                        bfsQueue.addLast(neighborWord)
                    }
                }
            }
        }
     }

     return 0
    }
}