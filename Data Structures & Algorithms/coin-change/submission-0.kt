class Solution {
    fun coinChange(coinValues: IntArray, targetAmount: Int): Int {
        val dp = IntArray(targetAmount + 1) { targetAmount + 1 }
        dp[0] = 0
        for (currentAmount in 1..targetAmount) {
            for (coin in coinValues) {
                if (coin <= currentAmount) {
                    dp[currentAmount] = minOf(
                        dp[currentAmount],
                        dp[currentAmount - coin] + 1
                    )
                }
            }
        }
        return if (dp[targetAmount] > targetAmount) -1 else dp[targetAmount]
    }
}
