class Solution {
    fun getConcatenation(nums: IntArray): IntArray {
        val size = nums.size
        val newArray = IntArray(size * 2)
        for (index in nums.indices) {
            newArray[index] = nums[index]
            newArray[index + size] = nums[index]
        }
        return newArray
    }
}