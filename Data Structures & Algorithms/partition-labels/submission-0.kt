class Solution {
    fun partitionLabels(inputText: String): List<Int> {
        
        val lastPositionByCharacter = HashMap<Char, Int>()

        inputText.forEachIndexed { index, character -> 
            lastPositionByCharacter[character] = index
        }

        val partitionSizes = mutableListOf<Int>()
        var currentPartitionSize = 0
        var currentPartitionEnd = 0

        for (currentIndex in inputText.indices) {
            currentPartitionSize++

            currentPartitionEnd = maxOf(
                currentPartitionEnd,
                lastPositionByCharacter[inputText[currentIndex]] ?: 0
            )

            if (currentIndex == currentPartitionEnd) {
                partitionSizes.add(currentPartitionSize)
                currentPartitionSize = 0
            }
        }

        return partitionSizes
    }
}