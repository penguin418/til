<<<<<<< HEAD
n, q = map(int, sys.stdin.readline())
pqr = []
qs = []
for _ in range(n):
    pqr.append(list(map(int, sys.stdin.readline())))
for _ in range(q):
    qs.append(list(map(int, sys.stdin.readline())))
=======
import sys

## 입력
n, _q = map(int, sys.stdin.readline().split())
graph = dict()

for _ in range(n-1):
    p, q, r = map(int, sys.stdin.readline().split())
    graph.setdefault(p, dict())
    graph.setdefault(q, dict())
    graph[p][q] = r
    graph[q][p] = r
## 테스트케이스
answers = []
for ii in range(_q):
    k, v = map(int, sys.stdin.readline().split())
    v = v-1
    ## 각각의 노드까지의 거리 계산
    ans = 0
    ## DFS
    visit = [False]*n
    visit[v] = True
    queue = [v]
    while len(queue):
        cur = queue.pop()
        for e in graph[cur].keys():
            ## k보다 작으면 그 이후 탐색도 모두 유사도가 k가 된다
            graph_cur_e = graph[cur][e]
            if not visit[e] and graph_cur_e >= k:
                ans += 1
                visit[e] = True
                queue.append(e)
            ## 한번 작아지면 더이상 탐사할 필요가 없다
    answers.append(ans)
print('\n'.join(map(str, answers)))
>>>>>>> algorithm
