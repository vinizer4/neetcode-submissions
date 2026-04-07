class Solution {
    fun findCheapestPrice(
        totalCities: Int, 
        flights: Array<IntArray>, 
        startingCity: Int, 
        destinationCity: Int, 
        maxStops: Int
    ): Int {
        
        val minCostToCity = IntArray(totalCities) { Int.MAX_VALUE }
        minCostToCity[startingCity] = 0

        val maxFlightsAllowed = maxStops + 1

        repeat(maxFlightsAllowed) {
            val currentLevelMinCost = minCostToCity.copyOf()

            for (flight in flights) {
                val origin = flight[0]
                val destination = flight[1]
                val ticketPrice = flight[2]

                if (minCostToCity[origin] == Int.MAX_VALUE) continue

                val costToReachOrigin = minCostToCity[origin]
                val totalCostToDest = costToReachOrigin + ticketPrice

                if (totalCostToDest < currentLevelMinCost[destination]) {
                    currentLevelMinCost[destination] = totalCostToDest
                }
            }

            currentLevelMinCost.copyInto(minCostToCity)
        }

        val finalCost = minCostToCity[destinationCity]

        return if (finalCost == Int.MAX_VALUE) -1 else finalCost
    }
}