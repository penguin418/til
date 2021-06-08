# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(maps):
    n, m = len(maps), len(maps[0])
    visits = \
        [
            [1234567890 if maps[j][i] == 1 else 0 for i in range(m)]
            for j in range(n)
        ]

    stack = []
    stack.append([0, 0])
    visits[0][0] = 1
    while len(stack):
        y, x = stack.pop(0)
        old = visits[y][x]
        if visits[y][x] > visits[n - 1][m - 1]:
            continue
        if y > 0 and visits[y - 1][x] == 1234567890:
            visits[y - 1][x] = old + 1
            stack.append([y - 1, x])
        if x > 0 and visits[y][x - 1] == 1234567890:
            visits[y][x - 1] = old + 1
            stack.append([y, x - 1])
        if y < n - 1 and visits[y + 1][x] == 1234567890:
            visits[y + 1][x] = old + 1
            stack.append([y + 1, x])
        if x < m - 1 and visits[y][x + 1] == 1234567890:
            visits[y][x + 1] = old + 1
            stack.append([y, x + 1])

    return -1 if visits[n - 1][m - 1] == 1234567890 else visits[n - 1][m - 1]