class Solution {
    fun removeElement(nums: IntArray, targetValue: Int): Int {
        var writePointer = 0

        for (readPointer in nums.indices) {
            if (nums[readPointer] != targetValue) {
                nums[writePointer] = nums[readPointer]
                writePointer++
            }
        }

        return writePointer
    }
}
