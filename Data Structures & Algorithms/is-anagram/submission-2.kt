class Solution {
    fun isAnagram(s: String, t: String): Boolean {

        if (s.length != t.length) return false

        val countS = mutableMapOf<Char, Int>()
        val countT = mutableMapOf<Char, Int>()

        for (i in s.indices) {
            countS[s[i]] = countS.getOrDefault(s[i], 0) + 1
            countT[t[i]] = countT.getOrDefault(t[i], 0) + 1
        }

        return countS == countT
    }
}
