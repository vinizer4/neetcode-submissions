class Solution {
    fun maxCoins(nums: IntArray): Int {
        
        if (nums.isEmpty()) return 0

        val n = nums.size
        val balloons = IntArray(n + 2)
        balloons[0] = 1
        balloons[n + 1] = 1

        for (index in 1..n) {
            balloons[index] = nums[index - 1]
        }

        val dp = Array(n + 2) { IntArray(n + 2) }

        for (length in 2 until n + 2) {
            for (left in 0 until n + 2 - length) {
                val right = left + length

                for (last in left + 1 until right) {
                    val coins = balloons[left] * balloons[last] * balloons[right]
                    val total = dp[left][last] + coins + dp[last][right]
                    dp[left][right] = max(dp[left][right], total)
                }
            }
        }

        return dp[0][n + 1]
    }
}