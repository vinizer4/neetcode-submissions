class Solution:
    def removeElement(self, nums: List[int], val: int) -> int:
        list_len = len(nums)
        write_index = 0

        for readable_index in range(list_len):
            if nums[readable_index] != val:
                nums[write_index] = nums[readable_index]
                write_index += 1
        
        return write_index