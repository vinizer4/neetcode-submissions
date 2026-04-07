class Solution {
    fun findItinerary(tickets: List<List<String>>): List<String> {
        val adj = HashMap<String, MutableList<String>>()

        tickets.sortedWith(compareBy({ it[0] }, { it[1] }))
            .reversed()
            .forEach { (src, dst) ->
                adj.getOrPut(src) { mutableListOf() }.add(dst)
            }

        val res = mutableListOf<String>()

        fun dfs(src: String) {
            while (adj[src]?.isNotEmpty() == true) {
                val dst = adj[src]!!.removeAt(adj[src]!!.lastIndex)
                dfs(dst)
            }
            res.add(src)
        }

        dfs("JFK")
        return res.reversed()
    }
}