class Solution {
    fun hasDuplicate(nums: IntArray): Boolean {
        val seen = HashSet<Int>()

        for (number in nums) {

            if (seen.contains(number)) {
                return true
            }
            
            seen.add(number)
        }

        return false
    }
}
