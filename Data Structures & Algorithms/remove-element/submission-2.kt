class Solution {
    fun removeElement(nums: IntArray, numToRemove: Int): Int {
        var writeIndex = 0

        for (readableIndex in nums.indices) {
            if (nums[readableIndex] != numToRemove) {
                nums[writeIndex] = nums[readableIndex]
                writeIndex++
            }
        }

        return writeIndex
    }
}