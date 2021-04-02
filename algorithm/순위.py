# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
# n번까지의 번호, 1:1방식 A보다 B가 강하면 항상이김, 결과 몇개를 분실함 -> 정확한 순위 개수
from collections import defaultdict
def solution(n, results):
    answer = 0
    
    plays = [[0] * n for _ in range(n)]
    for (win, lose) in results:
        win, lose = win-1, lose-1
        plays[win][lose] = 1
        plays[lose][win] = -1
    ## 와샬 알고리즘 (KIJ)
    for k in range(n):
        for i in range(n): # i가 
            for j in range(n): # j와 경기 결과를
                if plays[i][j] == 0: # 모른다면
                    if plays[i][k] and plays[i][k] == plays[k][j]: # i>k고 k>j면 i>j 혹은 반대
                        plays[i][j] = plays[i][k]
    
    # 0이 하나인 라인만 추리면
    for play in plays:
        cnt0 = 0
        for p in play:
            if p == 0:
                cnt0 += 1
        if cnt0 == 1:
            answer += 1
    return answer