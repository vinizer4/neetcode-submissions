class Solution {
    fun numDistinct(source: String, target: String): Int {
        
        val sourceLength = source.length
        val targetLength = target.length

        val dp = Array(sourceLength + 1) { IntArray(targetLength + 1) }

        for (sourceIndex in 0..sourceLength) {
            dp[sourceIndex][0] = 1
        }

        for (sourceIndex in 1..sourceLength) {
            for (targetIndex in 1..targetLength) {
                if (source[sourceIndex - 1] == target[targetIndex - 1]) {
                    dp[sourceIndex][targetIndex] =
                        dp[sourceIndex - 1][targetIndex - 1] + dp[sourceIndex - 1][targetIndex]
                } else {
                    dp[sourceIndex][targetIndex] = dp[sourceIndex - 1][targetIndex]
                }
            }
        }

        return dp[sourceLength][targetLength]
    }
}