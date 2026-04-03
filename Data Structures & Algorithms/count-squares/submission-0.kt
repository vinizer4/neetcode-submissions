class CountSquares() {

    private val pointFrequencyMap = HashMap<Pair<Int, Int>, Int>()
    private val allPoints = mutableListOf<IntArray>()


    fun add(point: IntArray) {
        val pointKey = Pair(point[0], point[1])
        pointFrequencyMap[pointKey] = pointFrequencyMap.getOrDefault(pointKey, 0) + 1
        allPoints.add(point)
    }

    fun count(queryPoint: IntArray): Int {
        var totalSquares = 0
        val (baseX, baseY) = queryPoint

        for ((currentX, currentY) in allPoints) {
            if (
                kotlin.math.abs(baseY - currentY) != kotlin.math.abs(baseX - currentX) ||
                currentX == baseX ||
                currentY == baseY
            ) continue

            val countPoint1 = pointFrequencyMap[Pair(currentX, baseY)] ?: 0
            
            val countPoint2 = pointFrequencyMap[Pair(baseX, currentY)] ?: 0

            totalSquares += countPoint1 * countPoint2
        }

        return totalSquares
    }

}