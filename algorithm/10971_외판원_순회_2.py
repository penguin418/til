# 10! < 1억, tsp
from sys import stdin
from typing import get_origin
nocity = int(stdin.readline()) # num of city
costs = [(list(map(int, stdin.readline().split()))) for _ in range(nocity)]

# 시간절약
links = []
for i in range(nocity):
    cur_links = []
    for j in range(nocity):
        if costs[i][j] > 0:
            cur_links.append(j)
    links.append(cur_links)

# 스택절약 
mincost = 1234567890
cost = 0
visit = [0] * nocity
def tsp(cur, depth):
    global mincost, cost
    if depth == nocity and 0 in links[cur]:
        mincost = min(mincost, cost + costs[cur][0])
    else:
        visit[cur] = 1
        for link in links[cur]:
            if not visit[link] > 0:
                cost += costs[cur][link]
                tsp(link, depth+1)
                cost -= costs[cur][link]
        visit[cur] = 0
tsp(0, 1)
print(mincost)