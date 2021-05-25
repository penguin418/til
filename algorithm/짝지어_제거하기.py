# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(s):
    stack = []
    for char in s:
        if len(stack):
            if char == stack[-1]:
                stack.pop()
                continue
        stack.append(char)
    return 1 if len(stack) == 0 else 0