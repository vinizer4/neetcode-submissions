class Solution {
    fun hasDuplicate(nums: IntArray): Boolean {
        // HashSet used to track numbers we have already seen
        // HashSet add() -> O(1) average
        // HashSet contains() -> O(1) average
        val seenNumbers = HashSet<Int>()
        
        // Iterate through every number in the array
        // Loop interation -> O(n)
        for (currentNumber in nums) {

            // Check if the number already exists in the set
            // contains() -> O(1)
            if (currentNumber in seenNumbers) {
                return true
            }

            // Add number to the set
            // add() -> O(1)
            seenNumbers.add(currentNumber)
        }

        // If we finish the loop, no duplicates exits
        return false
    }
}
