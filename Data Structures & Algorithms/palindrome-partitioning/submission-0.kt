class Solution {
    fun partition(inputText: String): List<List<String>> {
        
        val allPalindromePartitions = mutableListOf<List<String>>()
        val currentPartition = mutableListOf<String>()

        fun generatePalindromePartitionsDFS(
            currentStartIndex: Int,
            currentEndIndex: Int
        ) {

            if (currentEndIndex >= inputText.length) {
                if (currentStartIndex == currentEndIndex) {
                    allPalindromePartitions.add(currentPartition.toList())
                }
                return
            }

            if (isPalindrome(inputText, currentStartIndex, currentEndIndex)) {
                currentPartition.add(
                    inputText.substring(currentStartIndex, currentEndIndex + 1)
                )

                generatePalindromePartitionsDFS(
                    currentEndIndex + 1,
                    currentEndIndex + 1
                )

                currentPartition.removeAt(currentPartition.size - 1)
            }

            generatePalindromePartitionsDFS(
                currentStartIndex,
                currentEndIndex + 1
            )
        }

        generatePalindromePartitionsDFS(0, 0)
        return allPalindromePartitions
    }

    private fun isPalindrome(
        inputText: String,
        leftIndex: Int,
        rightIndex: Int
    ): Boolean {
        var leftPointer = leftIndex
        var rightPointer = rightIndex

        while (leftPointer < rightPointer) {
            if (inputText[leftPointer] != inputText[rightPointer]) {
                return false
            }
            leftPointer++
            rightPointer--
        }

        return true
    }
}