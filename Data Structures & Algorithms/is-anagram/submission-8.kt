class Solution {
    fun isAnagram(s: String, t: String): Boolean {

        if (s.length != t.length) return false

        val sCharCount = mutableMapOf<Char, Int>()
        val tCharCount = mutableMapOf<Char, Int>()

        for (index in s.indices) {
            sCharCount[s[index]] = sCharCount.getOrDefault(s[index], 0) + 1
            tCharCount[t[index]] = tCharCount.getOrDefault(t[index], 0) + 1
        }

        return sCharCount == tCharCount
    }
}
