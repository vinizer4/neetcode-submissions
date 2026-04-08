class Solution {
    fun canPartition(nums: IntArray): Boolean {
        var totalSum = nums.sum()
        if (totalSum % 2 != 0) return false
        val targetSum = totalSum / 2
        val dp = BooleanArray(targetSum + 1)
        dp[0] = true
        for (currentNum in nums) {
            for (currentSum in targetSum downTo currentNum) {
                dp[currentSum] = dp[currentSum] || dp[currentSum - currentNum]
            }
        }
        return dp[targetSum]
    }
}