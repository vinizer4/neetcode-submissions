class Solution {
    fun getConcatenation(nums: IntArray): IntArray {
        val newIntArraySize = nums.size * 2
        val newIntArray = IntArray(newIntArraySize)

        var insertionIndex = 0
        var nextInsertionIndex = nums.size

        for (index in nums.indices) {
            newIntArray[index] = nums[index]
            newIntArray[index + nextInsertionIndex] = nums[index]
        }

        return newIntArray
    }
}