class Solution {
    fun longestIncreasingPath(matrix: Array<IntArray>): Int {
        
        if (matrix.isEmpty() || matrix[0].isEmpty()) return 0

        val rowCount = matrix.size
        val colCount = matrix[0].size

        val memoCache = Array(rowCount) { IntArray(colCount) }

        val directions = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 0),
            intArrayOf(0, -1),
            intArrayOf(-1, 0)
        )

        fun dfs(row: Int, col: Int): Int {
            
            if (memoCache[row][col] != 0) return memoCache[row][col]

            var maxPath = 1

            for (dir in directions) {
                val nextRow = row + dir[0]
                val nextCol = col + dir[1]

                if (nextRow in 0 until rowCount && nextCol in 0 until colCount
                    && matrix[nextRow][nextCol] > matrix[row][col]) {
                        val pathLen = 1 + dfs(nextRow, nextCol)
                        maxPath = maxOf(maxPath, pathLen)
                    }
            }

            memoCache[row][col] = maxPath
            return maxPath
        }

        var globalMax = 0

        for (rowIndex in 0 until rowCount) {
            for (colIndex in 0 until colCount) {
                val currentPath = dfs(rowIndex, colIndex)
                globalMax = maxOf(globalMax, currentPath)
            }
        }

        return globalMax
    }
}