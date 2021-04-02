# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges

def solution(n, costs):
    answer = 0
    
    # 크루스칼
    costs.sort(key=lambda k: k[2])
    connection = [i for i in range(n)]
    for cost in costs:
        left = cost[0]
        right = cost[1]
        while connection[left] != left:
            left = connection[left]
        while connection[right] != right:
            right = connection[right]
            
        if left == right:
            continue
        else:
            if left < right:
                connection[right] = left
                answer += cost[2]
            else: 
                connection[left] = right
                answer += cost[2]
    
    return answer