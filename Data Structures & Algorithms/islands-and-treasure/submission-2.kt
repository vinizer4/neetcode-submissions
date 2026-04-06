class Solution {
    fun islandsAndTreasure(rooms: Array<IntArray>) {
        val totalRows = rooms.size
        val totalColumns = rooms[0].size

        val bfsQueue: ArrayDeque<Pair<Int, Int>> = ArrayDeque()

        for (rowIndex in 0 until totalRows) {
            for (columnIndex in 0 until totalColumns) {
                if (rooms[rowIndex][columnIndex] == 0) {
                    bfsQueue.addLast(Pair(rowIndex, columnIndex))
                }
            }
        }

        if (bfsQueue.isEmpty()) return

        val directions = arrayOf(
            intArrayOf(-1, 0),
            intArrayOf(0, -1),
            intArrayOf(1, 0),
            intArrayOf(0, 1)
        )

        while (bfsQueue.isNotEmpty()) {
            val (currentRow, currentColumn) = bfsQueue.removeFirst()

            for (direction in directions) {
                val neighborRow = currentRow + direction[0]
                val neighborColumn = currentColumn + direction[1]

                if (
                    neighborRow !in 0 until totalRows ||
                    neighborColumn !in 0 until totalColumns ||
                    rooms[neighborRow][neighborColumn] != Int.MAX_VALUE
                ) continue

                rooms[neighborRow][neighborColumn] =
                    rooms[currentRow][currentColumn] + 1

                bfsQueue.addLast(Pair(neighborRow, neighborColumn))
            }
        }
    }
}
