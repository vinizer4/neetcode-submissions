class Solution {
    fun maxProfit(prices: IntArray): Int {

        if (prices.isEmpty()) return 0
        
        var hold = -prices[0]
        var sold = 0
        var rest = 0

        for (index in 1 until prices.size) {

            val previousHold = hold
            val previousSold = sold
            val previousRest = rest

            hold = maxOf(previousHold, previousRest - prices[index])
            sold = previousHold + prices[index]
            rest = maxOf(previousRest, previousSold)
        }
        
        return maxOf(sold, rest)
    }
}