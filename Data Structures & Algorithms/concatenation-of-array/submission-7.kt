class Solution {
    fun getConcatenation(nums: IntArray): IntArray {
        val size = nums.size

        val result = IntArray(size * 2)

        for (index in nums.indices) {
            result[index] = nums[index]
            result[index + size] = nums[index]
        }

        return result
    }
}
