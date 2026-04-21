class Solution {
    fun longestCommonPrefix(strs: Array<String>): String {
        if (strs.isEmpty()) return ""

        val referenceString = strs[0]

        for (referenceCharPositionIndex in referenceString.indices) {

            val referenceChar = referenceString[referenceCharPositionIndex]

            for (comparisonStringIndex in 1 until strs.size) {
                val comparisonString = strs[comparisonStringIndex]
                val comparisonChar = comparisonString[referenceCharPositionIndex]

                if (
                    referenceCharPositionIndex >= comparisonString.length ||
                    comparisonChar != referenceChar
                ) {
                    return referenceString.substring(0, referenceCharPositionIndex)
                }
            }
        }

        return referenceString
    }
}
