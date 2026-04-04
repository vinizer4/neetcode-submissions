class Solution {
    fun networkDelayTime(
        directedEdges: Array<IntArray>, 
        totalNodes: Int, 
        startNode: Int)
    : Int {
        
        val adjacencyList = HashMap<Int, MutableList<Pair<Int, Int>>>()

        for ((sourceNode, targetNode, travelTime) in directedEdges) {
            adjacencyList
                .computeIfAbsent(sourceNode) { mutableListOf() }
                .add(Pair(targetNode, travelTime))
        }

        val minTimeHeap = PriorityQueue<Pair<Int, Int>>(
            compareBy { it.first }
        )

        minTimeHeap.offer(Pair(0, startNode))

        val visitedNodes = HashSet<Int>()
        var maxSignalTime = 0

        while (minTimeHeap.isNotEmpty()) {
            
            val (currentTime, currentNode) = minTimeHeap.poll()

            if (currentNode in visitedNodes) {
                continue
            }

            visitedNodes.add(currentNode)
            maxSignalTime = currentTime

            adjacencyList[currentNode]?.forEach { (neighborNode, edgeWeight) ->
                if (neighborNode !in visitedNodes) {
                    minTimeHeap.offer(
                        Pair(currentTime + edgeWeight, neighborNode)
                    )
                }
            }
        }

        return if (visitedNodes.size == totalNodes) {
            maxSignalTime
        } else {
            -1
        }

    }
}
