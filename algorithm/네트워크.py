# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(n, computers):
    answer = 0
        
    parents = [i for i in range(n)]
    for i in range(n):
        for j in range(i+1, n):
            if computers[i][j] > 0:
                print(i, j, parents[i], parents[j])
                ai = i
                while ai != parents[ai]:
                    ai = parents[ai]
                aj = j
                while aj != parents[aj]:
                    aj = parents[aj]
                if ai < aj:
                    parents[aj] = ai
                else:
                    parents[ai] = aj
    for i in range(n):
        ai = i
        while ai != parents[ai]:
            ai = parents[ai]
        parents[i] = ai

    groups = set(parents)
    
    return len(groups)