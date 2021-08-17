# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(answers):
    answer = []
    picks = [
        [1,2,3,4,5],
        [2,1,2,3,2,4,2,5],
        [3,3,1,1,2,2,4,4,5,5]]
    max_score = -1
    for pi, pick in enumerate(picks):
        score = 0
        i = 0
        for ans in answers:
            if ans == pick[i]:
                score += 1
            i = (i+1) % len(pick)
        if max_score < score:
            answer = [pi+1]
            max_score = score
        elif max_score== score:
            answer.append(pi+1)
    return answer