class Solution {
    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        val res = mutableListOf<Int>()
        if (matrix.isEmpty() || matrix[0].isEmpty()) return res

        var left = 0
        var right = matrix[0].size
        var top = 0
        var bottom = matrix.size

        while (left < right && top < bottom) {
            for (i in left until right) {
                res.add(matrix[top][i])
            }
            top++

            for (i in top until bottom) {
                res.add(matrix[i][right - 1])
            }
            right--

            if (!(left < right && top < bottom)) break

            for (i in right -1 downTo left) {
                res.add(matrix[bottom - 1][i])
            }
            bottom--

            for (i in bottom - 1 downTo top) {
                res.add(matrix[i][left])
            }
            left++
        }

        return res
    }
}
