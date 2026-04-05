class Solution {
    fun findOrder(
        totalCourses: Int, 
        prerequisites: Array<IntArray>
    ): IntArray {
        
        val adjacencyList = Array(totalCourses) {
            mutableListOf<Int>()
        }

        val indegreeByCourse = IntArray(totalCourses)

        for ((nextCourse, prerequisiteCourse) in prerequisites) {
            indegreeByCourse[nextCourse]++
            adjacencyList[prerequisiteCourse].add(nextCourse)
        }

        val courseOrder = mutableListOf<Int>()

        fun processAvailableCourseDFS(currentCourse: Int) {
            courseOrder.add(currentCourse)
            indegreeByCourse[currentCourse]--

            for (dependentCourse in adjacencyList[currentCourse]) {
                indegreeByCourse[dependentCourse]--

                if (indegreeByCourse[dependentCourse] == 0) {
                    processAvailableCourseDFS(dependentCourse)
                }
            }
        }

        for (course in 0 until totalCourses) {
            if (indegreeByCourse[course] == 0) {
                processAvailableCourseDFS(course)
            }
        }

        return if (courseOrder.size == totalCourses) {
            courseOrder.toIntArray()
        } else {
            intArrayOf()
        }
    }
}