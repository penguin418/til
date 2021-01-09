# 조합, N개 중 M개 고르기, 중복되지 않는 오름차순
from sys import stdin
n, m = list(map(int, stdin.readline().split()))
nums = list(map(int, stdin.readline().split()))

nums.sort()

from itertools import combinations
for c in combinations(nums, m):
    print(*c)
        
