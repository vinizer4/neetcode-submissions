class Solution {
    fun longestCommonPrefix(strs: Array<String>): String {

        if (strs.isEmpty()) return ""

        val referenceStr = strs[0]

        for (referenceCharIndex in referenceStr.indices) {

            val referenceChar = referenceStr[referenceCharIndex]

            for (comparationStrIndex in 1 until strs.size) {

                val comparationStr = strs[comparationStrIndex]

                if (
                    referenceCharIndex >= comparationStr.length ||
                    referenceChar != comparationStr[referenceCharIndex]
                ) return referenceStr.substring(0, referenceCharIndex)
            }
        }

        return referenceStr
    }
}
