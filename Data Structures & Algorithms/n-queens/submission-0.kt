class Solution {
    fun solveNQueens(boardSize: Int): List<List<String>> {
        
        val usedColumns = HashSet<Int>()
        val usedPositiveDiagonals = HashSet<Int>()
        val usedNegativeDiagonals = HashSet<Int>()

        val allValidBoards = mutableListOf<List<String>>()
        val chessBoard = Array(boardSize) { CharArray(boardSize) { '.' } }

        fun placeQueensBacktracking(currentRow: Int) {
            
            if (currentRow == boardSize) {
                allValidBoards.add(chessBoard.map { it.joinToString("") })
                return
            }

            for (currentColumn in 0 until boardSize) {
                if (
                    currentColumn in usedColumns ||
                    (currentRow + currentColumn) in usedPositiveDiagonals ||
                    (currentRow - currentColumn) in usedNegativeDiagonals
                ) continue

                usedColumns.add(currentColumn)
                usedPositiveDiagonals.add(currentRow + currentColumn)
                usedNegativeDiagonals.add(currentRow - currentColumn)

                chessBoard[currentRow][currentColumn] = 'Q'

                placeQueensBacktracking(currentRow + 1)

                usedColumns.remove(currentColumn)
                usedPositiveDiagonals.remove(currentRow + currentColumn)
                usedNegativeDiagonals.remove(currentRow - currentColumn)

                chessBoard[currentRow][currentColumn] = '.'
            }
        }

        placeQueensBacktracking(0)
        return allValidBoards
    }
}
