from itertools import permutations
n, m = list(map(int,input().split()))

nlist = list(range(1,n+1))
for p in permutations(nlist, m):
    print(p)