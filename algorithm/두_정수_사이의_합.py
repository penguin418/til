# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(a, b):
    if a == b:
        return a
    
    s = 0
    if b > a:
        for n in range(a, b+1):
            s += n
        return s
    else:
        for n in range(b, a+1):
            s += n
        return s