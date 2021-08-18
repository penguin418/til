# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
import sys
sys.setrecursionlimit(10000000)
def solution(a, edges):
    if sum(a):
        return -1
    
    adj = [[] for _ in range(len(a))]
    for e in edges:
        adj[e[0]].append(e[1])
        adj[e[1]].append(e[0])
    
    v = [0 for _ in range(len(a))]
    
    global answer
    answer = 0
    def dfs(curi=0, lasti=0, a2=a, v2=v):
        global answer
        for newi in adj[curi]:
            if not v2[newi]:
                v2[newi] = True
                dfs(newi, curi, a2, v2)
        a2[lasti] += a2[curi]
        answer += abs(a2[curi])
        a2[curi] = 0
    dfs()
    
    return answer