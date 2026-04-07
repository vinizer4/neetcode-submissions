class Solution {
    fun minCostClimbingStairs(cost: IntArray): Int {
        val totalSteps = cost.size

        for (currentStep in totalSteps - 3 downTo 0) {
            val costIfOneStep = cost[currentStep + 1]
            val costIfTwoSteps = cost[currentStep + 2]

            cost[currentStep] += minOf(costIfOneStep, costIfTwoSteps)
        }

        return minOf(cost[0], cost[1])
    }
}