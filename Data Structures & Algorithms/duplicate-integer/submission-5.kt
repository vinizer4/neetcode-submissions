class Solution {
    fun hasDuplicate(nums: IntArray): Boolean {
        val seenBefore = HashSet<Int>()
        for (num in nums) {
            if (seenBefore.contains(num)) return true
            seenBefore.add(num)
        }
        return false
    }
}
