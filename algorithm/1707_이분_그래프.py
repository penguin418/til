from sys import stdin

k = int(stdin.readline())

NOT_VISIT = 0
G1 = 1
G2 = 2
def check_bg(ve, visit, result):
    start = -1
    for v, e in enumerate(ve):
        if len(e) > 0:
            start = v
            if visit[start] != 0:
                continue
            bfsq = [start]
            visit[start] = G1
            while len(bfsq) > 0:
                cur = bfsq.pop(0)
                curg = visit[cur]
                for e in ve[cur]:
                    if not visit[e]:
                        visit[e] = 3 - curg
                        bfsq.append(e)
                    if visit[e] == curg:
                        result[0] = False

for _ in range(k):
    v, e = list(map(int, stdin.readline().split()))
    ve = [[] for i in range(v+1)]
    for _ in range(e):
        a,b = list(map(int, stdin.readline().split()))
        ve[a].append(b)
        ve[b].append(a)
    visit = [0] * (v+1)
    result = [True]
    check_bg(ve, visit, result)
    print("YES") if result[0] else print("NO")