# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
from collections import defaultdict

def solution(n, edge):
    if n == 2: return 1
    tree = defaultdict(set)
    
    for ab in edge:
        a, b = ab
        if a > b:
            a,b = b,a
        tree[a].add(b)
        tree[b].add(a)
    # print(tree)
    mostDeep = 0 # 가장 먼
    mostDeeps =[] # 가장 먼 것들
    visit = [0] * (n+1)
    visit[1] = 1
    q = [[1,0]]
    while len(q):
        node, deep = q.pop(0)
        deep += 1
        childs = list(filter(lambda x: not visit[x], tree[node]))
        
        if len(childs) and deep > mostDeep:
            mostDeep = deep
            mostDeeps = []
        for child in childs:
            visit[child] = 1
            q.append([child, deep])
            if mostDeep == deep:
                mostDeeps.append(child)
        # print(q,childs,mostDeep, mostDeeps)
    return len(mostDeeps)