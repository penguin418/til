from sys import stdin, setrecursionlimit
setrecursionlimit(100000)
while True:
    w, h = map(int, stdin.readline().split())
    if w == 0 and h == 0:
        break
    m = [[] for _ in range(h)]
    v = [[0]*w for _ in range(h)]
    for i in range(h):
        m[i].extend(list(map(int, stdin.readline().split())))
    def dfs(y, x):
        cnt = 0
        dirX = [1, 1, 0,-1,-1,-1,0,1]
        dirY = [0,-1,-1,-1, 0, 1,1,1]
        for dy, dx in zip(dirY, dirX):
            nY = y + dy
            nX = x + dx
            if nY < 0 or nY == h or nX < 0 or nX == w:
                continue
            if m[nY][nX] != 1:
                continue
            if  v[nY][nX] > 1:
                continue
            else:
                v[nY][nX] = 1
            m[nY][nX] = c
            cnt += 1
            bfs(nY, nX)
        return cnt
    c = 2
    for y in range(h):
        for x in range(w):
            v[y][x] = 1
            if m[y][x] == 1:
                m[y][x] = c
                dfs(y, x)
                c += 1
    print(c-2)