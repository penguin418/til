from sys import stdin
n, m = list(map(int, stdin.readline().split()))
nums = list(map(int, stdin.readline().split()))

nums.sort()

from itertools import permutations
for c in permutations(nums, m):
    print(*c)