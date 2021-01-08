from sys import stdin
from itertools import combinations

length = list(map(int, stdin.readline().split()))
chars = list(map(str, stdin.readline().split()))
chars = sorted(chars)
typeA = ['a', 'e', 'i', 'o', 'u']
typeB = [c for c in chars if c not in typeA]
typeA = [c for c in chars if c not in typeB]

for pick in combinations(chars, length[0]):
    cntA = 0
    cntB = 0
    for tA in typeA:
        if tA in pick: 
            cntA = 1
            break
    for tB in typeB:
        if tB in pick: 
            cntB+=1
        if cntB > 1: break
    if cntA > 0 and cntB > 1:
        print(''.join(pick))
