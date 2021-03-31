# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(s):
    answer = True
    
    p = s.lower().count('p')
    y = s.lower().count('y')
    print(p, y)
    return p == y
    