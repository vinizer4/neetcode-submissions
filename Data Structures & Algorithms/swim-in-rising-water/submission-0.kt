class Solution {
    fun swimInWater(elevationGrid: Array<IntArray>): Int {
        
        val gridSize = elevationGrid.size

        val directions = listOf(
            Pair(0, 1),
            Pair(0, -1),
            Pair(1, 0),
            Pair(-1, 0)
        )

        val minTimeHeap = PriorityQueue(
            compareBy<Pair<Int, Pair<Int, Int>>> { it.first }
        )

        minTimeHeap.offer(Pair(elevationGrid[0][0], Pair(0,0)))

        val visitedCells = HashSet<Pair<Int, Int>>()
        visitedCells.add(Pair(0, 0))

        while (minTimeHeap.isNotEmpty()) {

            val (currentTime, currentPosition) = minTimeHeap.poll()
            val (currentRow, currentColumn) = currentPosition

            if (currentRow == gridSize - 1 && currentColumn == gridSize - 1) {
                return currentTime
            }

            for ((rowOffSet, columnOffSet) in directions) {

                val neighborRow = currentRow + rowOffSet
                val neighborColumn = currentColumn + columnOffSet
                val neighborPosition = Pair(neighborRow, neighborColumn)

                if (
                    neighborRow !in 0 until gridSize ||
                    neighborColumn !in 0 until gridSize ||
                    neighborPosition in visitedCells
                ) {
                    continue
                }

                visitedCells.add(neighborPosition)

                val nextTime = maxOf(
                    currentTime,
                    elevationGrid[neighborRow][neighborColumn]
                )

                minTimeHeap.offer(Pair(nextTime, neighborPosition))
            }
        }

        return -1
    }
}
