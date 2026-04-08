class Solution {
    fun maxProduct(nums: IntArray): Int {
        var maxProductEndingHere = nums[0]
        var minProductEndingHere = nums[0]
        var globalMaxProduct = nums[0]

        for (currentIndex in 1 until nums.size) {
            val currentValue = nums[currentIndex]

            val candidates = intArrayOf(
                currentValue,
                currentValue * maxProductEndingHere,
                currentValue * minProductEndingHere
            )
            maxProductEndingHere = candidates.maxOrNull()!!
            minProductEndingHere = candidates.minOrNull()!!
            if (maxProductEndingHere > globalMaxProduct) {
                globalMaxProduct = maxProductEndingHere
            }
        }

        return globalMaxProduct
    }
}