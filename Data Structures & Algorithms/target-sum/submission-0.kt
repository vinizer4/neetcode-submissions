class Solution {
    fun findTargetSumWays(nums: IntArray, target: Int): Int {
        val dp = mutableMapOf(0 to 1)

        for (num in nums) {
            val nextDp = mutableMapOf<Int, Int>()
            for ((total, count) in dp) {
                nextDp[total + num] = nextDp.getOrDefault(total + num, 0) + count
                nextDp[total - num] = nextDp.getOrDefault(total - num, 0) + count
            }
            dp.clear()
            dp.putAll(nextDp)
        }
        
        return dp[target] ?: 0
    }
}
