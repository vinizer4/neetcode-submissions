class Solution {
    fun multiply(numberA: String, numberB: String): String {
        
        if (numberA == "0" || numberB == "0") return "0"

        val resultDigits = IntArray(numberA.length + numberB.length)

        for (indexA in numberA.indices.reversed()) {
            for (indexB in numberB.indices.reversed()) {
                val digitA = numberA[indexA] - '0'
                val digitB = numberB[indexB] - '0'

                val position = (numberA.length - 1 - indexA) +
                               (numberB.length - 1 - indexB)

                val multiplicationResult = digitA * digitB

                resultDigits[position] += multiplicationResult
                resultDigits[position + 1] += resultDigits[position] / 10
                resultDigits[position] %= 10
            }
        }

        var lastNonZeroIndex = resultDigits.size - 1
        while (lastNonZeroIndex >= 0 && resultDigits[lastNonZeroIndex] == 0) {
            lastNonZeroIndex--
        }

        if (lastNonZeroIndex < 0) return "0"

        return buildString {
            for (index in lastNonZeroIndex downTo 0) {
                append(resultDigits[index])
            }
        }
    }
}