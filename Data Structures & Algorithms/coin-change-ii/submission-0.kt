class Solution {
    fun change(amount: Int, coins: IntArray): Int {
        val waysToMake = IntArray(amount + 1)

        waysToMake[0] = 1

        for (coinValue in coins) {
            for (currentAmount in coinValue..amount) {
                waysToMake[currentAmount] += waysToMake[currentAmount - coinValue]
            }
        }

        return waysToMake[amount]
    }
}