from sys import stdin

n = int(stdin.readline())
grid = []
for i in range(n):
    inputline = list( map(int, list(stdin.readline().strip())))
    grid.append(inputline)
visit = [[0 for _ in range(n)] for _ in range(n)]

dirX = [0,1,0,-1]
dirY = [1,0,-1,0]
def cc_dfs(y, x, group): # connected copmonent
    if visit[y][x]: return 0 # 방문
    visit[y][x] = 1

    if grid[y][x] == 0: return 0 # 0이면 무시
    grid[y][x] = group # 그룹 추가후 재 탐색
    same_group = 1
    for dx, dy in zip(dirX, dirY):
        yy = y+dy
        xx = x+dx
        if yy < 0: continue
        if yy == n: continue
        if xx < 0: continue
        if xx == n: continue
        same_group += cc_dfs(yy,xx,group)
    return same_group

groups = []
cnt = 1
for i in range(n):
    for j in range(n):
        res = cc_dfs(i, j, cnt)
        if res > 0:
            groups.append(res)
        cnt += 1

groups.sort()

print(len(groups))
for gp in groups:
    print(gp)