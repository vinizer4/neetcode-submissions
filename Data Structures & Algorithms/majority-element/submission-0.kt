class Solution {
    fun majorityElement(nums: IntArray): Int {
        val count = HashMap<Int, Int>()
        var res = 0
        var maxCount = 0

        for (num in nums) {
            count[num] = count.getOrDefault(num, 0) + 1
            if (count[num]!! > maxCount) {
                res = num
                maxCount = count[num]!!
            }
        }

        return res
    }
}
