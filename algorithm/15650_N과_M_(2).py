from itertools import permutations
n, m = list(map(int,input().split()))

nlist = list(range(1,n+1))
for p in permutations(nlist, m):
    cannot_print = False
    for i in range(len(p)-1):
        if p[i] > p[i+1]:
            cannot_print = True
    if cannot_print:
        continue
    print(*p)