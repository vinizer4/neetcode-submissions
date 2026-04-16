class Solution {
    fun isAnagram(s: String, t: String): Boolean {
        
        if (s.length != t.length) return false

        val frequencyMap = HashMap<Char, Int>()

        for (char in s) {
            frequencyMap[char] = (frequencyMap[char] ?: 0) + 1
        }

        for (char in t) {
            val currentCount = frequencyMap[char] ?: 0
            if (currentCount == 0) return false
            frequencyMap[char] = currentCount - 1
        }

        return true
    }
}