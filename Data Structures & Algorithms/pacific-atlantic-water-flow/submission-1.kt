class Solution {
    fun pacificAtlantic(heights: Array<IntArray>): List<List<Int>> {
        val totalRows = heights.size
        val totalColumns = heights[0].size

        val pacificReachableCells = HashSet<Pair<Int, Int>>()
        val atlanticReachableCells = HashSet<Pair<Int, Int>>()

        fun markReachableCellsDFS(
            currentRow: Int,
            currentColumn: Int,
            visitedCells: HashSet<Pair<Int, Int>>,
            previousHeight: Int
        ) {
            val currentCell = currentRow to currentColumn

            if (
                currentCell in visitedCells ||
                currentRow < 0 ||
                currentColumn < 0 ||
                currentRow == totalRows ||
                currentColumn == totalColumns ||
                heights[currentRow][currentColumn] < previousHeight
            ) {
                return
            }

            visitedCells.add(currentCell)

            val currentHeight = heights[currentRow][currentColumn]

            markReachableCellsDFS(currentRow + 1, currentColumn, visitedCells, currentHeight)
            markReachableCellsDFS(currentRow - 1, currentColumn, visitedCells, currentHeight)
            markReachableCellsDFS(currentRow, currentColumn + 1, visitedCells, currentHeight)
            markReachableCellsDFS(currentRow, currentColumn - 1, visitedCells, currentHeight)
        }

        for (columnIndex in 0 until totalColumns) {
            markReachableCellsDFS(
                0,
                columnIndex,
                pacificReachableCells,
                heights[0][columnIndex]
            )

            markReachableCellsDFS(
                totalRows - 1,
                columnIndex,
                atlanticReachableCells,
                heights[totalRows - 1][columnIndex]
            )
        }

        for (rowIndex in 0 until totalRows) {
            markReachableCellsDFS(
                rowIndex,
                0,
                pacificReachableCells,
                heights[rowIndex][0]
            )

            markReachableCellsDFS(
                rowIndex,
                totalColumns - 1,
                atlanticReachableCells,
                heights[rowIndex][totalColumns - 1]
            )
        }

        return (0 until totalRows).flatMap { rowIndex ->
            (0 until totalColumns).mapNotNull { columnIndex ->
                val currentCell = rowIndex to columnIndex
                if (
                    currentCell in pacificReachableCells &&
                    currentCell in atlanticReachableCells
                ) {
                    listOf(rowIndex, columnIndex)
                } else {
                    null
                }
            }
        }
    }
}