class Solution {
    fun topologicalSort(n: Int, edges: Array<IntArray>): List<Int> {
        val adj = HashMap<Int, MutableList<Int>>()

        for (i in 0 until n) {
            adj[i] = mutableListOf()
        }

        for (edge in edges) {
            adj[edge[0]]!!.add(edge[1])
        }

        val topSort = mutableListOf<Int>()
        val visited = HashSet<Int>()
        val visiting = HashSet<Int>()

        for (i in 0 until n) {
            if (!dfs(i, adj, visited, visiting, topSort)) {
                return emptyList() // Return empty list if a cycle is detected
            }
        }

        topSort.reverse()
        return topSort
    }

    private fun dfs(src: Int, adj: Map<Int, List<Int>>, visited: MutableSet<Int>, visiting: MutableSet<Int>, topSort: MutableList<Int>): Boolean {
        if (visited.contains(src)) return true
        if (visiting.contains(src)) return false // A cycle is detected

        visiting.add(src)
        for (neighbor in adj[src]!!) {
            if (!dfs(neighbor, adj, visited, visiting, topSort)) return false // A cycle is detected
        }

        visiting.remove(src)
        visited.add(src)
        topSort.add(src)

        return true // No cycle detected
    }
}
