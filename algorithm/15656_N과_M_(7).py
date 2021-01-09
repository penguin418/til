# n개 중 m개, 중복 오름차순, product
from sys import stdin
n, m = list(map(int, stdin.readline().split()))
nums = list(map(int, stdin.readline().split()))

nums.sort()

from itertools import product
for c in product(nums, repeat= m):
    print(*c)