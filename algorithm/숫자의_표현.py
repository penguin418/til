# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
# 연속된 수로 표현하는 방법을 찾아야 하므로, r * m + ( 1부터 m까지 ) = n
def solution(n):
    answer = 0
    
    i = 0
    _sum = 0
    while _sum < n:
        _sum += i
        i += 1
        if (n-_sum) % i == 0:
            answer += 1
    return answer