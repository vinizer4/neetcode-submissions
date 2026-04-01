class Solution {
    fun isNStraightHand(hand: IntArray, groupSize: Int): Boolean {

        if (hand.size % groupSize != 0) return false

        val count = HashMap<Int, Int>()
        hand.forEach { count[it] = count.getOrDefault(it, 0) + 1 }

        for (num in hand) {
            var start = num
            while (count.getOrDefault(start -1, 0) > 0) {
                start--
            }

            while (start <= num) {
                while (count.getOrDefault(start, 0) > 0) {
                    for (i in start until start + groupSize) {
                        if (count.getOrDefault(i, 0) == 0) {
                            return false
                        }
                        count[i] = count[i]!! - 1
                    }
                }
                start++
            }
        }
        return true
    }
}
