class Solution {
    fun myPow(x: Double, n: Int): Double {
        fun helper(x: Double, n: Int): Double {
            if (x == 0.0) {
                return 0.0
            }
            if (n == 0) {
                return 1.0
            }

            val res = helper(x * x, n / 2)
            return if (n % 2 != 0) x * res else res
        }

        val res = helper(x, Math.abs(n))
        return if (n >= 0) res else 1 / res
    }
}
