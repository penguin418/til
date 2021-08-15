# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
import sys
def solution(numbers, target):
    
    answers = 0
    q = [[-1,0]]
    while len(q):
        deep, total = q.pop()
        # print(deep, total)
        if deep == len(numbers) - 1:
            answers += 1 if total == target else 0
        else:
            q.append([deep+1,total-numbers[deep+1]])
            q.append([deep+1,total+numbers[deep+1]])
    
    
    return answers