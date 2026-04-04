class Solution {
    fun maxProbability(
        totalNodes: Int, 
        edges: Array<IntArray>, 
        edgeSuccessProbabilities: DoubleArray, 
        startNode: Int, 
        endNode: Int
    ): Double {
        
        val adjacencyList = Array(totalNodes) {
            mutableListOf<Pair<Int, Double>>()
        }

        for (edgeIndex in edges.indices) {

            val sourceNode = edges[edgeIndex][0]
            val destinationNode = edges[edgeIndex][1]
            val edgeProbability = edgeSuccessProbabilities[edgeIndex]

            adjacencyList[sourceNode].add(destinationNode to edgeProbability)
            adjacencyList[destinationNode].add(sourceNode to edgeProbability)
        }

        val bestProbabilityByNode = DoubleArray(totalNodes)
        bestProbabilityByNode[startNode] = 1.0

        val maxProbabilityHeap = PriorityQueue<Pair<Int, Double>>(
            compareByDescending { it.second }
        )
        maxProbabilityHeap.offer(Pair(startNode, 1.0))

        while (maxProbabilityHeap.isNotEmpty()) {
            val (currentNode, currentProbability) = maxProbabilityHeap.poll()

            if (currentNode == endNode) {
                return currentProbability
            }

            if (currentProbability < bestProbabilityByNode[currentNode]) {
                continue
            }

            for ((neighborNode, edgeProbability) in adjacencyList[currentNode]) {
                val newProbability = currentProbability * edgeProbability

                if (newProbability > bestProbabilityByNode[neighborNode]) {
                    bestProbabilityByNode[neighborNode] = newProbability
                    maxProbabilityHeap.offer(neighborNode to newProbability)
                }
            }
        }

        return 0.0
    }
}
