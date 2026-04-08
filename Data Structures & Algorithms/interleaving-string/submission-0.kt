class Solution {
    fun isInterleave(s1: String, s2: String, s3: String): Boolean {
        val s1Length = s1.length
        val s2Length = s2.length
        if (s1Length + s2Length != s3.length) return false

        val canForm = Array(s1Length + 1) { BooleanArray(s2Length + 1) }
        canForm[0][0] = true

        for (s1Used in 1..s1Length) {
            canForm[s1Used][0] = canForm[s1Used - 1][0] &&
                                 s1[s1Used - 1] == s3[s1Used - 1]
        }
        for (s2Used in 1..s2Length) {
            canForm[0][s2Used] = canForm[0][s2Used - 1] &&
                                 s2[s2Used - 1] == s3[s2Used - 1]
        }
        for (s1Used in 1..s1Length) {
            for (s2Used in 1..s2Length) {
                val fromS1 = canForm[s1Used - 1][s2Used] &&
                             s1[s1Used - 1] == s3[s1Used + s2Used - 1]
                val fromS2 = canForm[s1Used][s2Used - 1] &&
                             s2[s2Used - 1] == s3[s1Used + s2Used - 1]
                canForm[s1Used][s2Used] = fromS1 || fromS2
            }
        }
        return canForm[s1Length][s2Length]
    }
}