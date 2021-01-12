from sys import stdin
n, m = list(map(int, stdin.readline().split()))
link_inputs = [list(map(int, stdin.readline().split())) for _ in range(m)]
link = [[] for _ in range(n)]
for ll in link_inputs:
    link[ll[0]].append(ll[1])
    link[ll[1]].append(ll[0])

visit = [0] * n
def dfs(cur, cnt):
    if cnt == 4:
        print(1)
        import sys
        sys.exit()
    else:
        for l in link[cur]:
            if not visit[l]:
                visit[l] = 1
                dfs(l, cnt+1)
                visit[l] = 0
for l in range(n):
    visit[l] = 1
    dfs(l, 0)
    visit[l] = 0
print(0)

