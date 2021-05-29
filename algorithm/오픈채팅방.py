// 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
from collections import defaultdict
def solution(record):
    answer = []
    names = dict()
    related_answer = defaultdict(list)
    for message in record:
        message = message.split()
        if message[0] == 'Enter':
            answer.append('%s님이 들어왔습니다.' % message[1])
            names[message[1]] = message[2]
            related_answer[message[1]].append(len(answer)-1)
        elif message[0] == 'Leave':
            answer.append('%s님이 나갔습니다.' % message[1])
            related_answer[message[1]].append(len(answer)-1)
        else:
            names[message[1]] = message[2]
    for name in names:
        for i in related_answer[name]:
            answer[i] = answer[i].replace(name, names[name], 1)
    return answer