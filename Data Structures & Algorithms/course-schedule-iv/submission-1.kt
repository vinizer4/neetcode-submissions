class Solution {
    fun checkIfPrerequisite(
        totalCourses: Int, 
        prerequisites: Array<IntArray>, 
        queries: Array<IntArray>
    ): List<Boolean> {
        
        val adjacencyList = Array(totalCourses) {
            mutableSetOf<Int>()
        }

        val prerequisiteSetByCourse = Array(totalCourses) {
            mutableSetOf<Int>()
        }

        val indegreeByCourse = IntArray(totalCourses)

        for (prerequisitePair in prerequisites) {
            val prerequisiteCourse = prerequisitePair[0]
            val nextCourse = prerequisitePair[1]

            adjacencyList[prerequisiteCourse].add(nextCourse)
            indegreeByCourse[nextCourse]++
        }
        
        val availableCoursesQueue = ArrayDeque<Int>()

        for (course in 0 until totalCourses) {
            if (indegreeByCourse[course] == 0) {
                availableCoursesQueue.add(course)
            }
        }

        while (availableCoursesQueue.isNotEmpty()) {
            val currentCourse = availableCoursesQueue.removeFirst()

            for (dependentCourse in adjacencyList[currentCourse]) {
                prerequisiteSetByCourse[dependentCourse].add(currentCourse)
                prerequisiteSetByCourse[dependentCourse]
                    .addAll(prerequisiteSetByCourse[currentCourse])

                indegreeByCourse[dependentCourse]--

                if (indegreeByCourse[dependentCourse] == 0) {
                    availableCoursesQueue.add(dependentCourse)
                }
            }
        }

        return queries.map { queryPair ->
            val prerequisiteCourse = queryPair[0]
            val targetCourse = queryPair[1]
            
            prerequisiteSetByCourse[targetCourse]
                .contains(prerequisiteCourse)
        }
    }
}