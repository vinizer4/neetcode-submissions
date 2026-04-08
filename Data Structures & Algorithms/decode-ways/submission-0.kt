class Solution {
    fun numDecodings(s: String): Int {
        val stringLength = s.length
        val dp = IntArray(stringLength + 1)
        dp[stringLength] = 1
        for (index in stringLength - 1 downTo 0) {
            if (s[index] == '0') {
                dp[index] = 0
            } else {
                dp[index] = dp[index + 1]
                if (index + 1 < stringLength) {
                    val twoDigit = (s[index] - '0') * 10 + (s[index + 1] - '0')
                    if (twoDigit in 10..26) {
                        dp[index] += dp[index + 2]
                    }
                }
            }
        }
        return dp[0]
    }
}