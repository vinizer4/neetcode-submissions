class Solution {
    fun isAnagram(firstString: String, secondString: String): Boolean {

        if (firstString.length != secondString.length) return false

        val characterCounts = HashMap<Char, Int>()

        for (currentIndex in 0 until firstString.length) {
            val charFromFirst = firstString[currentIndex]
            val charFromSecond = secondString[currentIndex]

            characterCounts[charFromFirst] = characterCounts.getOrDefault(charFromFirst, 0) + 1
            characterCounts[charFromSecond] = characterCounts.getOrDefault(charFromSecond, 0) - 1
        }

        for (count in characterCounts.values) {
            if (count != 0) return false
        }

        return true
    }
}
