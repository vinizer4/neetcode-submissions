class Solution {
    fun rob(houses: IntArray): Int {
        if (houses.size == 1) return houses[0]

        val scenarioSkipFirst = robLinearStreet(houses, start = 1, end = houses.size - 1)
        val scenarioSkipLast = robLinearStreet(houses, start = 0, end = houses.size - 2)

        return maxOf(scenarioSkipFirst, scenarioSkipLast)
    }

    private fun robLinearStreet(houses: IntArray, start: Int, end: Int): Int {
        var maxTwoHousesBack = 0
        var maxOneHouseBack = 0

        for (i in start..end) {
            val currentHouseValue = houses[i]

            val maxIfRobCurrent = maxTwoHousesBack + currentHouseValue
            val maxIfSkipCurrent = maxOneHouseBack

            val newMaxLucro = maxOf(maxIfRobCurrent, maxIfSkipCurrent)

            maxTwoHousesBack = maxOneHouseBack
            maxOneHouseBack = newMaxLucro
        }

        return maxOneHouseBack
    }
}