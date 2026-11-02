class Solution {
    fun isAnagram(firstString: String, secondString: String): Boolean {

        if (firstString.length != secondString.length) return false

        val firstCharCount = HashMap<Char, Int>()
        val secondCharCount = HashMap<Char, Int>()

        for (index in firstString.indices) {
            val firstChar = firstString[index]
            val secondChar = secondString[index]

            firstCharCount[firstChar] = firstCharCount.getOrDefault(firstChar, 0) + 1
            secondCharCount[secondChar] = secondCharCount.getOrDefault(secondChar, 0) + 1
        }

        return firstCharCount == secondCharCount
    }
}
