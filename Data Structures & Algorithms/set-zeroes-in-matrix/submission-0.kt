class Solution {
    fun setZeroes(matrix: Array<IntArray>): Unit {
        val totalRows = matrix.size
        val totalColumns = matrix[0].size

        val rowsToZero = BooleanArray(totalRows)
        val colsToZero = BooleanArray(totalColumns)

        for (rowIndex in 0 until totalRows) {
            for (columnIndex in 0 until totalColumns) {
                if (matrix[rowIndex][columnIndex] == 0) {
                    rowsToZero[rowIndex] = true
                    colsToZero[columnIndex] = true
                }
            }
        }

        for (rowIndex in 0 until totalRows) {
            for (columnIndex in 0 until totalColumns) {
                if (rowsToZero[rowIndex] || colsToZero[columnIndex]) {
                    matrix[rowIndex][columnIndex] = 0
                }
            }
        }
    }
}