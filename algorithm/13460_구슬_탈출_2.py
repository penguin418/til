import sys
n, m = list(map(int, sys.stdin.readline().split()))
grid = []
start_r = []
start_b = []
for i  in range(n):
    inputs = list(sys.stdin.readline()[:-1])
    for j in range(m):
        if inputs[j] == 'R':
            start_r = [i, j]
        elif inputs[j] == 'B':
            start_b = [i, j]
    grid.append(inputs)
dir_x = [1, 0, -1, 0]
dir_y = [0, -1, 0, 1]
q = [[0, * start_r, * start_b, -1]]
def move(new_y, new_x, dy, dx, color):
    if grid[new_y][new_x] != 'O': # 빠지지 않았다면 이동
        while True:
            grid[new_y][new_x] = '.'
            new_y += dy
            new_x += dx
            if grid[new_y][new_x] != '.':
                if grid[new_y][new_x] == 'O': 
                    return new_y, new_x
                new_y -= dy
                new_x -= dx
                break
        grid[new_y][new_x] = color
    return new_y, new_x
visit = {}
while len(q) > 0:
    cnt, r_y, r_x, b_y, b_x, d = q.pop(0)
    visit[''.join(map(str, [r_y, r_x, b_y, b_x]))] = 1    
    for i, (dx, dy) in enumerate(zip(dir_x, dir_y)):
        if i == d:
            continue
        grid[r_y][r_x] = 'R'
        grid[b_y][b_x] = 'B'
        nr_y, nr_x = move(r_y, r_x, dy, dx, 'R')
        nb_y, nb_x = move(b_y, b_x, dy, dx, 'B')
        nr_y, nr_x = move(nr_y, nr_x, dy, dx, 'R') # 2번째 이동
        nb_y, nb_x = move(nb_y, nb_x, dy, dx, 'B')
        if grid[nr_y][nr_x] != 'O': # 지도에서 지우기
            grid[nr_y][nr_x] = '.'
        if grid[nb_y][nb_x] != 'O': # 지도에서 지우기
            grid[nb_y][nb_x] = '.'
        if ''.join(map(str, [nr_y, nr_x, nb_y, nb_x])) in visit:
            continue
        if grid[nb_y][nb_x] == 'O':
            continue
        if grid[nr_y][nr_x] == 'O':
            print(cnt+1)
            sys.exit(0)
        if cnt < 9:
            q.append([cnt+1, nr_y, nr_x, nb_y, nb_x, i])
print(-1)