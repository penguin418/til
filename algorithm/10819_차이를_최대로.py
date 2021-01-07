# 8! * 8 < 1억
from sys import stdin
import itertools

_ = int(stdin.readline())
seq = list(map(int, stdin.readline().split()))

def perform(seq):
    total = 0
    for i in range(1, len(seq)):
        total += abs(seq[i-1] - seq[i])
    return total

max = 0
for p in itertools.permutations(seq):
    t = perform(list(p))
    max = t if t > max else max
print(max)