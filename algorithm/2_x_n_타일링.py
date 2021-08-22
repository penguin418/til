# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(n):
    answer = [0,1,2,3]
    if n<=3:
        return answer[n]
    
    for i in range(4,n+1):
        answer.append(
            (answer[i-1] + answer[i-2]) % 1000000007
        )
        #print(i, answer[i], answer[i-1], answer[i-2])
    return answer[n]