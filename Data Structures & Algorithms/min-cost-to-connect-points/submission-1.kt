class Solution {
    fun minCostConnectPoints(points: Array<IntArray>): Int {
        val totalPoints = points.size
        val adjacencyList = HashMap<Int, MutableList<Pair<Int, Int>>>()

        for (pointAIndex in 0 until totalPoints) {
            val pointAX = points[pointAIndex][0]
            val pointAY = points[pointAIndex][1]

            for (pointBIndex in pointAIndex + 1 until totalPoints) {
                val pointBX = points[pointBIndex][0]
                val pointBY = points[pointBIndex][1]

                val edgeCost =
                    abs(pointAX - pointBX) + abs(pointAY - pointBY)

                adjacencyList
                    .computeIfAbsent(pointAIndex) { mutableListOf() }
                    .add(edgeCost to pointBIndex)

                adjacencyList
                    .computeIfAbsent(pointBIndex) { mutableListOf() }
                    .add(edgeCost to pointAIndex)
            }
        }

        var minimumTotalCost = 0
        val visitedPoints = mutableSetOf<Int>()
        val minimumCostHeap = PriorityQueue(
            compareBy<Pair<Int, Int>> { it.first }
        )

        minimumCostHeap.add(0 to 0)

        while (visitedPoints.size < totalPoints) {
            val (currentCost, currentPoint) = minimumCostHeap.poll()

            if (currentPoint in visitedPoints) {
                continue
            }

            minimumTotalCost += currentCost
            visitedPoints.add(currentPoint)

            for ((neighborCost, neighborPoint) in adjacencyList[currentPoint] ?: emptyList()) {
                if (neighborPoint !in visitedPoints) {
                    minimumCostHeap.add(neighborCost to neighborPoint)
                }
            }
        }

        return minimumTotalCost
    }
}