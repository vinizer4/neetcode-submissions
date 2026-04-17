class Solution {
    fun longestCommonPrefix(strs: Array<String>): String {
        if (strs.isEmpty()) return ""

        val referenceString = strs[0]

        for (charPosition in referenceString.indices) {
            val referenceChar = referenceString[charPosition]

            for (comparisonIndex in 1 until strs.size) {
                val comparisonString = strs[comparisonIndex]

                if (
                    charPosition >= comparisonString.length || 
                    comparisonString[charPosition] != referenceChar
                    ) {
                        return referenceString.substring(0, charPosition)
                    }
            }
        }

        return referenceString
    }
}