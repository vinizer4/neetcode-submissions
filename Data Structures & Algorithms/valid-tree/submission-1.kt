class Solution {
    fun validTree(
        totalNodes: Int, 
        edges: Array<IntArray>
    ): Boolean {
        
        if (edges.size > totalNodes - 1) return false

        val adjacencyList = Array(totalNodes) {
            mutableListOf<Int>()
        }

        for ((nodeA, nodeB) in edges) {
            adjacencyList[nodeA].add(nodeB)
            adjacencyList[nodeB].add(nodeA)
        }

        val visitedNodes = HashSet<Int>()

        fun detectCycleDFS(
            currentNode: Int,
            parentNode: Int
        ): Boolean {

            if (currentNode in visitedNodes) return false

            visitedNodes.add(currentNode)

            for (neighborNode in adjacencyList[currentNode]) {
                if (neighborNode == parentNode) continue

                if (!detectCycleDFS(neighborNode, currentNode)) return false
            }

            return true
        }

        return detectCycleDFS(0, -1) && visitedNodes.size == totalNodes
    }
}