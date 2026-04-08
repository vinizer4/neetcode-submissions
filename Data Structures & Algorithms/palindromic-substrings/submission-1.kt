class Solution {
    fun countSubstrings(s: String): Int {
        var totalPalindromes = 0
        val stringLength = s.length

        for (centerIndex in 0 until stringLength) {
            totalPalindromes += expandFromCenter(s, centerIndex, centerIndex)
            totalPalindromes += expandFromCenter(s, centerIndex, centerIndex + 1)
        }
        
        return totalPalindromes
    }

    private fun expandFromCenter(s: String, leftStart: Int, rightStart: Int): Int {
        var left = leftStart
        var right = rightStart
        var count = 0
        
        while (left >= 0 && right < s.length && s[left] == s[right]) {
            count++
            left--
            right++
        }
        
        return count
    }
}
