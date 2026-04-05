class UnionFind(totalNodes: Int) {
    private val parentByNode = IntArray(totalNodes) { it }
    private val componentSizeByRoot = IntArray(totalNodes) { 1 }

    fun find(node: Int): Int {
        if (parentByNode[node] != node) {
            parentByNode[node] = find(parentByNode[node])
        }
        return parentByNode[node]
    }

    fun union(nodeA: Int, nodeB: Int): Boolean {
        val rootA = find(nodeA)
        val rootB = find(nodeB)

        if (rootA == rootB) {
            return false
        }

        if (componentSizeByRoot[rootA] > componentSizeByRoot[rootB]) {
            parentByNode[rootB] = rootA
            componentSizeByRoot[rootA] += componentSizeByRoot[rootB]
        } else {
            parentByNode[rootA] = rootB
            componentSizeByRoot[rootB] += componentSizeByRoot[rootA]
        }

        return true
    }

    fun largestComponentSize(): Int {
        return componentSizeByRoot.maxOrNull() ?: 0
    }
}

class Solution {
    fun findCriticalAndPseudoCriticalEdges(
        totalNodes: Int,
        edges: Array<IntArray>
    ): List<List<Int>> {
        val sortedEdgesWithOriginalIndex = edges
            .mapIndexed { originalIndex, edge ->
                intArrayOf(edge[0], edge[1], edge[2], originalIndex)
            }
            .sortedBy { it[2] }

        var originalMstWeight = 0
        val baseUnionFind = UnionFind(totalNodes)

        for (edge in sortedEdgesWithOriginalIndex) {
            if (baseUnionFind.union(edge[0], edge[1])) {
                originalMstWeight += edge[2]
            }
        }

        val criticalEdgeIndices = mutableListOf<Int>()
        val pseudoCriticalEdgeIndices = mutableListOf<Int>()

        for (candidateEdge in sortedEdgesWithOriginalIndex) {
            val candidateNodeA = candidateEdge[0]
            val candidateNodeB = candidateEdge[1]
            val candidateWeight = candidateEdge[2]
            val candidateOriginalIndex = candidateEdge[3]

            val unionFindWithoutCandidate = UnionFind(totalNodes)
            var mstWeightWithoutCandidate = 0

            for (otherEdge in sortedEdgesWithOriginalIndex) {
                if (otherEdge[3] == candidateOriginalIndex) {
                    continue
                }

                if (unionFindWithoutCandidate.union(otherEdge[0], otherEdge[1])) {
                    mstWeightWithoutCandidate += otherEdge[2]
                }
            }

            if (
                unionFindWithoutCandidate.largestComponentSize() != totalNodes ||
                mstWeightWithoutCandidate > originalMstWeight
            ) {
                criticalEdgeIndices.add(candidateOriginalIndex)
                continue
            }

            val unionFindWithCandidate = UnionFind(totalNodes)
            unionFindWithCandidate.union(candidateNodeA, candidateNodeB)
            var mstWeightWithCandidate = candidateWeight

            for (otherEdge in sortedEdgesWithOriginalIndex) {
                if (unionFindWithCandidate.union(otherEdge[0], otherEdge[1])) {
                    mstWeightWithCandidate += otherEdge[2]
                }
            }

            if (mstWeightWithCandidate == originalMstWeight) {
                pseudoCriticalEdgeIndices.add(candidateOriginalIndex)
            }
        }

        return listOf(criticalEdgeIndices, pseudoCriticalEdgeIndices)
    }
}