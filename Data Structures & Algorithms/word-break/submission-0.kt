class Solution {
    fun wordBreak(s: String, wordDict: List<String>): Boolean {
        val wordSet = HashSet<String>(wordDict)
        val stringLength = s.length

        val dp = BooleanArray(stringLength + 1)
        dp[0] = true

        for (endIndex in 1..stringLength) {
            for (startIndex in 0 until endIndex) {
                if (dp[startIndex] && wordSet.contains(s.substring(startIndex, endIndex))) {
                    dp[endIndex] = true
                    break
                }
            }
        }

        return dp[stringLength]
    }
}