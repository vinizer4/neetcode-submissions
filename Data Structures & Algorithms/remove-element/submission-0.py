from typing import List

class Solution:
    def removeElement(self, numbers: List[int], val: int) -> int:
        write_index = 0
        
        for read_index in range(len(numbers)):
            if numbers[read_index] != val:
                numbers[write_index] = numbers[read_index]
                write_index += 1
        
        return write_index