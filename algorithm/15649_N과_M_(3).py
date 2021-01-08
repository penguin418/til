# product 
from itertools import product
n, m = list(map(int,input().split()))

nlist = list(range(1,n+1))
for p in product(nlist, repeat=m):
    print(*p)