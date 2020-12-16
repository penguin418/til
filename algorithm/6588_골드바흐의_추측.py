def find_primes(maxn):
    cand = [1] * (maxn+1)
    cand[0] = cand[1] = 0
    for i in range(2, maxn+1):
        if cand[i] > 0:
            for j in range(2*i, maxn+1, i):
                cand[j] = 0
    return cand

ns = []
while 1:
    new_n = int(input())
    if new_n == 0: break
    ns.append(new_n)

primes = find_primes(max(ns))
for n in ns:
    for c in range(3,n+1):
        if primes[c] > 0 and primes[n-c] > 0:
            print('%d = %d + %d' % (n, c, (n-c)))
            break # 골드바흐의 추측은 100만 이하에서 모두 성립하므로 예외를 생각하지 않아도 된다
