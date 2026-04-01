class Solution {
    fun mergeTriplets(
        triplets: Array<IntArray>, 
        targetTriplet: IntArray
    ): Boolean {
        
        val achievedTargetPositions = HashSet<Int>()

        for (currentTriplet in triplets) {
            if (
                currentTriplet[0] > targetTriplet[0] ||
                currentTriplet[1] > targetTriplet[1] ||
                currentTriplet[2] > targetTriplet[2]
            ) {
                continue
            }

            for ((index, value) in currentTriplet.withIndex()) {
                if (value == targetTriplet[index]) {
                    achievedTargetPositions.add(index)
                }
            }
        }

        return achievedTargetPositions.size == 3
    }
}
