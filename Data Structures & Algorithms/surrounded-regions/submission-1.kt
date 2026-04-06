class Solution {
    fun solve(board: Array<CharArray>) {
        val totalRows = board.size
        val totalColumns = board[0].size

        fun markBorderConnectedRegionDFS(
            currentRow: Int,
            currentColumn: Int
        ) {
            if (
                currentRow < 0 ||
                currentColumn < 0 ||
                currentRow == totalRows ||
                currentColumn == totalColumns ||
                board[currentRow][currentColumn] != 'O'
            ) {
                return
            }

            board[currentRow][currentColumn] = 'T'

            markBorderConnectedRegionDFS(currentRow + 1, currentColumn) // baixo
            markBorderConnectedRegionDFS(currentRow - 1, currentColumn) // cima
            markBorderConnectedRegionDFS(currentRow, currentColumn + 1) // direita
            markBorderConnectedRegionDFS(currentRow, currentColumn - 1) // esquerda
        }

        for (rowIndex in 0 until totalRows) {
            if (board[rowIndex][0] == 'O') {
                markBorderConnectedRegionDFS(rowIndex, 0)
            }
            if (board[rowIndex][totalColumns - 1] == 'O') {
                markBorderConnectedRegionDFS(rowIndex, totalColumns - 1)
            }
        }

        for (columnIndex in 0 until totalColumns) {
            if (board[0][columnIndex] == 'O') {
                markBorderConnectedRegionDFS(0, columnIndex)
            }
            if (board[totalRows - 1][columnIndex] == 'O') {
                markBorderConnectedRegionDFS(totalRows - 1, columnIndex)
            }
        }

        for (rowIndex in 0 until totalRows) {
            for (columnIndex in 0 until totalColumns) {
                if (board[rowIndex][columnIndex] == 'O') {
                    board[rowIndex][columnIndex] = 'X'
                } else if (board[rowIndex][columnIndex] == 'T') {
                    board[rowIndex][columnIndex] = 'O'
                }
            }
        }
    }
}