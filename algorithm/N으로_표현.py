# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(N, number):
    # 0개, 1개의 N을 이용해 나타내는 방법
    memo = [0, {N}]
    for i in range(2,9):
        cur = {int(str(N)*i)}
        # memo를 조합하여 구하는 방법
        for j in range(1, i//2+1):
            for front in memo[j]:
                for back in memo[i-j]:
                    cur.add(front+back)
                    cur.add(front-back)
                    cur.add(front-back)
                    cur.add(front*back)
                    if back != 0:
                        cur.add(front//back)
                    if front != 0:
                        cur.add(back//front)
        if number in cur:
            return i
        memo.append(cur)
    return -1