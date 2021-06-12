# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(s):
    answer = ''
    nums = list(map(int, s.split()))
    
    return "%d %d" % (min(nums), max(nums))