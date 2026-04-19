/**
Group Anagrams

PROBLEM

We are given an array of strings.

Examplo:

["act","pots","tops","cat","stop","hat"]

We must group together all strings that are anagrams.

Definition:

Two words are anagrams if they contain the exact same characters
with the same frequency, but possibly in a different order.

Example:

act
cat

KEY IDEA

Two strings are anagrams if their character frequency is identical.

Example:

act

frequency vector:
[a,b,c,d,....,z]
[1,0,1,0,....,1]

The vectors match -> same group.

STRATEGY

Use a HashMap.

Key:
character frequency vector

Value:
list of strings with that frequency

Example:
[1,0,1,0,...] -> ["act","cat"]

Steps:

1. Iterate through each string
2. Build a frequency array of size 26
3. Use that array as the key in the HashMap
4. Append the string to the corresponding list
5. Return all grouped lists


TIME COMPLEXITY

O(n * k)

n = number of strings
k = average length of each string

Explanation:

- Each string is processed once
- Each character is counted once

SPACE COMPLEXITY

O(n * k)

Explanation:

All strings are stored inside the HashMap groups.
*/
class Solution {
    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        
        /**
        STEP 1
        Create the HashMap used for grouping.

        Key:
        character frequency list (size 26)

        Value:
        list of string that share that frequency

        HashMap creation -> O(1)
        */
        val anagramGroupsMap = HashMap<List<Int>, MutableList<String>>() // O(1)

        /**
        STEP 2
        Iterate through every string.

        Loop -> O(n)
        */
        for (currentWord in strs) {

            /**
            STEP 3
            Build frequency vector for the current word.

            MutableList creation (size 26) -> O(26) ~ O(1)
            */
            val characterFrequencyVector = MutableList(26) { 0 }

            /**
            STEP 4
            Count characters in the word.

            Loop -> O(k)
            */
            for (character in currentWord) {

                /**
                Convert character to index.

                'a' -> 0
                'b' -> 1
                ...
                'z' -> 25
                */
                val characterIndex = character - 'a' // O(1)

                /**
                Increment frequency.

                MutableList.get/set -> O(1)
                */
                characterFrequencyVector[characterIndex]++
            }

            /**
            STEP 5
            Insert the word into the corresponding group

            HashMap.getOrPut() -> O(1)
            MutableList.add() -> O(1)
            */
            anagramGroupsMap
                .getOrPut(characterFrequencyVector) { mutableListOf() }
                .add(currentWord)
        }

        /**
        STEP 6
        Return all grouped values.

        HashMap.values -> O(n)
        toList() -> O(n)
        */
        return anagramGroupsMap.values.toList()
    }
}