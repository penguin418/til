from sys import stdin

n, t = list(map(int, stdin.readline().split()))
x = 0
y = 0
dirs = [[1,0], [0,-1], [-1,0], [0,1]]
cur_dir = 0
last_t = 0
tt = 0
for _ in range(n):
    tt , d = list(stdin.readline().split())
    tt = int(tt)
    x += dirs[cur_dir][0] * (tt - last_t)
    y += dirs[cur_dir][1] * (tt - last_t)
    last_t = tt
    if d == 'right':
        cur_dir = (cur_dir + 1) % 4
    else:
        cur_dir = (cur_dir - 1) % 4
if t > tt:
    x += dirs[cur_dir][0] * (t - tt)
    y += dirs[cur_dir][1] * (t - tt)
print(x, y)
    