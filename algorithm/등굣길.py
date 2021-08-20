# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
PUDDLE = 1234567890
def solution(m, n, puddles):
    memo = [[0 for _ in range(m)] for _ in range(n)]
    for p1, p2 in puddles:
        memo[p2-1][p1-1] = PUDDLE
    memo[0][0] = 1
    for yi, y in enumerate(memo):
        for xi, x in enumerate(y):
            if memo[yi][xi] == PUDDLE:
                continue
            if yi == 0 and memo[yi][xi-1] != PUDDLE:
                memo[yi][xi] += memo[yi][xi-1]
            elif xi == 0 and memo[yi-1][xi] != PUDDLE:
                memo[yi][xi] += memo[yi-1][xi]
            else:
                if memo[yi][xi-1] != PUDDLE:
                    memo[yi][xi] += memo[yi][xi-1]
                if memo[yi-1][xi] != PUDDLE:
                    memo[yi][xi] += memo[yi-1][xi]
    return memo[n-1][m-1] % 1000000007