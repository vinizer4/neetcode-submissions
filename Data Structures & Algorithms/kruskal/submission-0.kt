import java.util.PriorityQueue

class UnionFind(n: Int) {
    private val par = HashMap<Int, Int>()
    private val rank = HashMap<Int, Int>()
    var count = n
        private set

    init {
        for (i in 0 until n) {
            par[i] = i
            rank[i] = 0
        }
    }

    // Find parent of n, with path compression.
    fun find(n: Int): Int {
        var p = par[n]!!
        while (p != par[p]) {
            par[p] = par[par[p]]!!
            p = par[p]!!
        }
        return p
    }

    // Union by height / rank.
    // Return false if already connected, true otherwise.
    fun union(n1: Int, n2: Int): Boolean {
        val p1 = find(n1)
        val p2 = find(n2)
        if (p1 == p2) {
            return false
        }

        if (rank[p1]!! > rank[p2]!!) {
            par[p2] = p1
        } else if (rank[p1]!! < rank[p2]!!) {
            par[p1] = p2
        } else {
            par[p1] = p2
            rank[p2] = rank[p2]!! + 1
        }

        count-- // Decrement count when two sets are unioned
        return true
    }
}

class Solution {
    // Implementation for Kruskal's algorithm to compute Minimum Spanning Trees
    fun minimumSpanningTree(edges: List<List<Int>>, n: Int): Int {
        val minHeap = PriorityQueue<List<Int>>(compareBy { it[0] })

        for (edge in edges) {
            val n1 = edge[0]
            val n2 = edge[1]
            val weight = edge[2]
            minHeap.add(listOf(weight, n1, n2))
        }

        val unionFind = UnionFind(n)
        var totalWeight = 0

        while (minHeap.isNotEmpty()) {
            val cur = minHeap.poll()
            val w1 = cur[0]
            val n1 = cur[1]
            val n2 = cur[2]
            if (unionFind.union(n1, n2)) {
                totalWeight += w1
            }
        }
        // Return -1 if not all nodes are visited (unconnected graph)
        return if (unionFind.count == 1) totalWeight else -1
    }
}
