# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(n, times):
    answer = float('inf')
    
    times.sort()
    left = times[0] # 1분에 1명 검사
    right = times[-1]*n # 마지막사람이 모두 검사
    while left <= right:
        mid = (left+right)//2
        mTime = float('inf')
        passengers = 0
        for time in times:
            passengers += mid // time
        if passengers < n:
            left = mid+1
        else:
            answer = min(answer, mid)
            right = mid-1
    return answer