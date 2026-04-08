class Solution {
    fun lengthOfLIS(nums: IntArray): Int {
        
        val tails = IntArray(nums.size)
        var size = 0

        for (currentNumber in nums) {

            var left = 0
            var right = size
            while (left < right) {
                val mid = left + (right - left) / 2
                if (tails[mid] < currentNumber) {
                    left = mid + 1
                } else {
                    right = mid
                }
            }

            tails[left] = currentNumber
            if (left == size) {
                size++
            }
        }

        return size
    }
}