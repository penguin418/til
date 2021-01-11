# n개 중 m개, 중복 뽑기, 비내림차순
from sys import stdin
n, m = list(map(int, stdin.readline().split()))
nums = list(map(int, stdin.readline().split()))

nums.sort()

def pick(arr, start, nxt):
    if start == m:
        print(*arr)
    else:
        prev = -1
        for i in range(nxt, len(nums)):
            if prev != nums[i]:
                arr[start] = nums[i]
                pick(arr, start+1, i)
                prev = nums[i]
pick([0]*m, 0, 0)