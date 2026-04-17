/*
14. Longest Common Prefix

- CLARIFYING QUESTIONS
1. What counts as a "common prefix"?
    (A prefix shared by ALL strings. If even one string disagrees at a
     position, the prefix stops there).
2. Can the array contain empty strings?
    (yes, strs[i].length can be 0, which means the common prefix is
     immediately "").
3. What if there's only one string?
    (That string itself is the longest common prefix).

- PROBLEM EXPLANATION
We want the longest starting substring that every string in the array
begins with. The moment any string disagrees at a character position,
that's our boundary.

- ALGORITHMIC PATTERN
Vertical Scanning (Column-by-Column comparison - the same idea as
checking columns in a matrix of characters).

- MAIN IDEA AND INTUITION
Imagine stacking every string vertically like rows in a grid

    f l o w e r
    f l o w
    f l i g h t

We scan column by column (left to right). As long as every row has the
same character in the current column, that character belongs to the
prefix. The instant we find a mismatch - or any string runs out of
characters - we stop.

We don't need a HashMap or sorting. We just use the first string as
our "reference ruler" and measure every other string against it, one
character position at a time.

- STRATEGY
- Step 1: Handle edge case - if the array if empty, return "".
- Step 2: Use the first string as the reference.
- Step 3: For each character in the reference string:
    - Step 3a: Get the current character from reference.
    - Step 3b: Compare it against the same position in EVERY other string.
    - Step 3c: If any string is too short OR has a different character ->
                return the prefix built so far (reference[0..position]).
- Step 4: If we exhaust the reference string with no mismatch,
          the entire first string is the common prefix.

- PREDICTED COMPLEXITY
Time: O(N * M), where N = strs.size (number of strings, <= 200)
      M = length of the shortest string
      We stop at first mismatch, so effective time is O(S) where
      S = total character actually compared.

Space: O(1) - Only index variables. Output substrings is O(M).
*/

class Solution {
    fun longestCommonPrefix(strs: Array<String>): String {
        
        // Step 1: Edge case - empty array means no prefix possible
        // O(1) time, O(1) space
        if (strs.isEmpty()) return ""

        // Step 2: Use the first string as the "reference ruler"
        val referenceString = strs[0]

        // Step 3: Scan column by column (each character position in reference)
        // Outer loop runs at most M times, where M = referenceString.length (<= 200)
        for (charPosition in referenceString.indices) {

            // Step 3a: The character we expect every string to hvae at this position
            val referenceChar = referenceString[charPosition]

            // Step 3b: Compare this character against every OTHER string
            // Inner loop runs N-1 times, where N = strs.size (<= 200)
            for (comparisonIndex in 1 until strs.size) {
                
                val comparisonString = strs[comparisonIndex]

                /*
                Step 3c: Two failure conditions:
                    1) comparisonString is too short (ran out of characters)
                    2) comparisonString has a different character at this position
                Either way, the prefix ends BEFORE this position
                O(1) time for each comparison
                */
                if (charPosition >= comparisonString.length ||
                    comparisonString[charPosition] != referenceChar) {

                        // Return everything from index 0 up to (but not including) charPosition
                        // O(M) time to create the substring, where M = charPosition
                        return referenceString.substring(0, charPosition)
                    }
            }
        }

        // Step 4: We checked every position in the reference withou a mismatch
        // The entire first string is the common prefix
        return referenceString
    }
}
