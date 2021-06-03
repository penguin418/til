# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(s):
    # 앞뒤 삭제
    inputs = s[2:-2]
    # 분리
    inputs = inputs.split('},{')
    # 길이로 정렬
    inputs = sorted(inputs, key=lambda x: len(x))
    # ,로 분리
    answer = []
    last = set([])
    for inp in inputs:
        nums = set(inp.split(','))
        num = nums.difference(last)
        answer.append(int(num.pop()))
        last = nums
    
    return answer