class Solution {
    fun checkValidString(s: String): Boolean {
        var leftMin = 0
        var leftMax = 0

        for (c in s) {
            when (c) {
                '(' -> {
                    leftMin++
                    leftMax++
                }
                ')' -> {
                    leftMin--
                    leftMax--
                }
                else -> {
                    leftMin--
                    leftMax++
                }
            }
            if (leftMax < 0) return false
            if (leftMin < 0) leftMin = 0
        }
        return leftMin == 0
    }
}
