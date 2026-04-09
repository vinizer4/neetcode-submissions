class Solution {
    fun minDistance(word1: String, word2: String): Int {
        
        val lengthWord1 = word1.length
        val lengthWord2 = word2.length

        val dp = Array(lengthWord1 + 1) { IntArray(lengthWord2 + 1) }

        for (indexWord1 in 0..lengthWord1) {
            dp[indexWord1][0] = indexWord1
        }

        for (indexWord2 in 0..lengthWord2) {
            dp[0][indexWord2] = indexWord2
        }

        for (indexWord1 in 1..lengthWord1) {
            for (indexWord2 in 1..lengthWord2) {
                if (word1[indexWord1 - 1] == word2[indexWord2 - 1]) {
                    dp[indexWord1][indexWord2] = dp[indexWord1 - 1][indexWord2 - 1]
                } else {
                    dp[indexWord1][indexWord2] = minOf(
                        dp[indexWord1 - 1][indexWord2] + 1,
                        dp[indexWord1][indexWord2 - 1] + 1,
                        dp[indexWord1 - 1][indexWord2 - 1] + 1
                    )
                }
            }
        }

        return dp[lengthWord1][lengthWord2]
    }
}