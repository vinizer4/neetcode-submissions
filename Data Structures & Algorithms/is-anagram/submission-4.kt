class Solution {
    fun isAnagram(s: String, t: String): Boolean {
        if (s.length != t.length) return false

        val countS = HashMap<Char, Int>()
        val countT = HashMap<Char, Int>()

        for (char in s) {
            val count = countS.getOrDefault(char, 0) + 1
            countS[char] = count
        }

        for (char in t) {
            val count = countT.getOrDefault(char, 0) + 1
            countT[char] = count
        }

        return countS == countT
    }
}
