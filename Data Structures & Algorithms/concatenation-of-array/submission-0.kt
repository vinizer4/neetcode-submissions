class Solution {
    fun getConcatenation(nums: IntArray): IntArray {
        val newIntArraySize = nums.size * 2
        val newIntArray = IntArray(newIntArraySize)

        var insertionIndex = 0
        var nextInsertionIndex = nums.size

        while (insertionIndex < newIntArraySize) {
            if (insertionIndex < nums.size) {
                newIntArray[insertionIndex] = nums[insertionIndex]
                insertionIndex++
            } else {
                newIntArray[insertionIndex] = nums[insertionIndex - nextInsertionIndex]
                insertionIndex++
            }
        }

        return newIntArray
    }
}