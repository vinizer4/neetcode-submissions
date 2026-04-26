class Solution {
    fun hasDuplicate(nums: IntArray): Boolean {
        val seen = HashSet<Int>()

        for (num in nums) {
            if (num in seen) return true
            seen.add(num)
        }
        return false
    }
}
