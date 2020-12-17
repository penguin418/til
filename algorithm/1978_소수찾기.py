c = int(input())
n = list(map(int, input().split()))

maxn = max(n)

cand = [1] * (maxn + 1)
cand[0] = cand[1] = 0

for i in range(2, maxn+1):
    if cand[i] > 0:
        for j in range(2*i, maxn+1, i):
            cand[j] = 0

primes = [i for i, is_p in enumerate(cand) if is_p > 0]

print(len([i for i in n if i in primes]))
