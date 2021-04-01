# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(bridge_length, weight, truck_weights):
    answer = 0
    
    bridge = [0] * bridge_length
    total_w = 0
    cnt = 0
    while total_w or len(truck_weights):
        out = bridge.pop()
        total_w -= out
        
        if len(truck_weights) and total_w + truck_weights[0] <= weight:
            total_w += truck_weights[0]
            bridge.insert(0,truck_weights.pop(0))
        else:
            bridge.insert(0,0)
        cnt += 1
    return cnt