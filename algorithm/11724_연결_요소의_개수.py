import sys
sys.setrecursionlimit(10000)
n, m = list(map(int, sys.stdin.readline().split()))

uv = [list(map(int, sys.stdin.readline().split())) for _ in range(m)]

links = [[] for _ in range(n+1)]
for link in uv:
    links[link[0]].append(link[1])
    links[link[1]].append(link[0])

visit = [0] * (n+1)
def dfs(start):
    visit[start] = 1
    for link in links[start]:
        if not visit[link]:
            dfs(link)
        
cnt = 0
for i in range(1, n+1):
    if not visit[i]:
        dfs(i)
        cnt += 1
print(cnt)
