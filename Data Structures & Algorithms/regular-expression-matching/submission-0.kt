class Solution {
    fun isMatch(inputString: String, patternString: String): Boolean {
        
        val memoizationMap = HashMap<Pair<Int, Int>, Boolean>()

        fun dp(inputIndex: Int, patternIndex: Int): Boolean {

            memoizationMap[Pair(inputIndex, patternIndex)]?.let { return it }

            if (patternIndex == patternString.length) {
                return inputIndex == inputString.length
            }

            val firstMatch = (inputIndex < inputString.length &&
            (patternString[patternIndex] == inputString[inputIndex] ||
             patternString[patternIndex] == '.'))

             if (patternIndex + 1 < patternString.length && patternString[patternIndex + 1] == '*') {

                val result = (dp(inputIndex, patternIndex + 2) ||
                             (firstMatch && dp(inputIndex + 1, patternIndex)))
                memoizationMap[Pair(inputIndex, patternIndex)] = result
                return result
             } else {
                val result = (firstMatch && dp(inputIndex + 1, patternIndex + 1))
                memoizationMap[Pair(inputIndex, patternIndex)] = result
                return result                
             }
        }

        return dp(0, 0)
    }
}