# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(land):
    dp = land[0][::] # 선택지 중 최대값
    for i in range(1, len(land)):
        last = dp[::] # 지난번 선택
        for j in range(4): # 마지막 라인
            last_max = 0
            for k in range(4): # 이전 라인
                if j == k: continue # j칸을 밟으려면 지난번에 j를 밟은 경우 패스 
                if last[k] > last_max:
                    last_max = last[k]
            dp[j] = land[i][j] + last_max
    return max(dp)