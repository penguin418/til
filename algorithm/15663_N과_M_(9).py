# n개 중 m개, 중복없이 증가하는 순열
from sys import stdin
n, m = list(map(int, stdin.readline().split()))
nums = list(map(int, stdin.readline().split()))

from itertools import permutations
for s in sorted([e for e in set(permutations(nums, m))]):
    print(*s)
