class Solution {
    fun longestPalindrome(inputString: String): String {
        val stringLength = inputString.length
        val isSubstringPalindrome = Array(stringLength) { BooleanArray(stringLength) }

        var maximumPalindromeLength = 0
        var bestStartIndex = 0

        for (startIndex in stringLength - 1 downTo 0) {
            for (endIndex in startIndex until stringLength) {

                val doEndsMatch = inputString[startIndex] == inputString[endIndex]
                val isStringSmall = (endIndex - startIndex) <= 2

                val isInnerSubstringPalindrome = isStringSmall ||
                    isSubstringPalindrome[startIndex + 1][endIndex - 1]

                if (doEndsMatch && isInnerSubstringPalindrome) {
                    isSubstringPalindrome[startIndex][endIndex] = true

                    val currentSubstringLength = endIndex - startIndex + 1

                    if (currentSubstringLength > maximumPalindromeLength) {
                        maximumPalindromeLength = currentSubstringLength
                        bestStartIndex = startIndex
                    }
                }
            }
        }

        val bestEndIndex = bestStartIndex + maximumPalindromeLength
        return inputString.substring(bestStartIndex, bestEndIndex)
    }
}