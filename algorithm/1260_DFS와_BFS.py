from sys import stdin

n, m, v = list(map(int, stdin.readline().split()))
vv = [[] for _ in range(n+1)]
for _ in range(m):
    a, b = list(map(int, stdin.readline().split()))
    vv[a].append(b)
    vv[b].append(a)

for i in range(1,n+1):
    vv[i].sort()

dfss = []
visit = [0] * (n+1)
def dfs(cur):
    if visit[cur] == 0:
        visit[cur] = 1
        dfss.append(cur)
        for link in vv[cur]:
            dfs(link)


bfss = []
bfsq = []
def bfs(start):
    bfsq.append(start)
    while len(bfsq) > 0:
        nxt = bfsq.pop(0)
        if visit[nxt] == 0:
            visit[nxt] = 1
            bfss.append(nxt)
            for link in vv[nxt]:
                bfsq.append(link)

dfs(v)
# reset visit
for i in range(len(visit)):
    visit[i] = 0

bfs(v)

print(* dfss)
print(* bfss)

