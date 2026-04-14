class Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val valueToIndex = HashMap<Int, Int>()
        for (currentIndex in nums.indices) {
            val complement = target - nums[currentIndex]
            if (valueToIndex.containsKey(complement)) {
                return intArrayOf(valueToIndex[complement]!!, currentIndex)
            }
            valueToIndex[nums[currentIndex]] = currentIndex
        }
        return intArrayOf()
    }
}