# n개 중 m개, 중복없는 비내림 오름차순
from sys import stdin
n, m = list(map(int, stdin.readline().split()))
nums = list(map(int, stdin.readline().split()))

nums.sort()

def product(arr, start, nxt):
    if start == m:
        print(*arr)
    else:
        for i in range(nxt, n):
            arr[start] = nums[i]
            product(arr, start+1, i)
product([0]*m, 0, 0)