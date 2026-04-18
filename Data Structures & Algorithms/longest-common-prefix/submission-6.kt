class Solution {
    fun longestCommonPrefix(strs: Array<String>): String {
        
        if (strs.isEmpty()) return ""

        val referenceString = strs[0]

        for (referenceCharPosition in referenceString.indices) {

            val referenceChar = referenceString[referenceCharPosition]

            for (comparisonStringIndex in 1 until strs.size) {

                val comparisonString = strs[comparisonStringIndex]

                if (
                    referenceCharPosition >= comparisonString.length ||
                    comparisonString[referenceCharPosition] != referenceChar
                ) return referenceString.substring(0, referenceCharPosition)
            }
        }

        return referenceString
    }
}