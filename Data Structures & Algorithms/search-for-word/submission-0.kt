class Solution {
    fun exist(
        board: Array<CharArray>, 
        targetWord: String
    ): Boolean {
        
        val totalRows = board.size
        val totalColumns = board[0].size
        val visitedCellsInCurrentPath = HashSet<Pair<Int, Int>>()

        fun searchWordBacktrackingDFS(
            currentRow: Int,
            currentColumn: Int,
            currentWordIndex: Int
        ): Boolean {

            if (currentWordIndex == targetWord.length) {
                return true
            }

            if (
                currentRow < 0 ||
                currentColumn < 0 ||
                currentRow >= totalRows ||
                currentColumn >= totalColumns ||
                board[currentRow][currentColumn] != targetWord[currentWordIndex] ||
                Pair(currentRow, currentColumn) in visitedCellsInCurrentPath
            ) return false

            visitedCellsInCurrentPath.add(Pair(currentRow, currentColumn))

            val wordFound =
                searchWordBacktrackingDFS(currentRow + 1, currentColumn, currentWordIndex + 1) ||
                searchWordBacktrackingDFS(currentRow - 1, currentColumn, currentWordIndex + 1) ||
                searchWordBacktrackingDFS(currentRow, currentColumn + 1, currentWordIndex + 1) ||
                searchWordBacktrackingDFS(currentRow, currentColumn - 1, currentWordIndex + 1)

            visitedCellsInCurrentPath.remove(Pair(currentRow, currentColumn))

            return wordFound
        }

        for (rowIndex in 0 until totalRows) {
            for (columnIndex in 0 until totalColumns) {
                if (searchWordBacktrackingDFS(rowIndex, columnIndex, 0)) {
                    return true
                }
            }
        }

        return false
    }
}