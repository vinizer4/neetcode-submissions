class Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val valueToIndex = HashMap<Int, Int>()

        for (index in nums.indices) {
            val currentValue = nums[index]
            val complementValue = target - currentValue

            if (valueToIndex.containsKey(complementValue)) {
                val complementIndex = valueToIndex[complementValue]!!
                return intArrayOf(complementIndex, index)
            }

            valueToIndex[currentValue] = index
        }

        return intArrayOf()
    }
}
