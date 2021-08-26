# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(numbers, hand):
    answer = ''
    costmap = {
        '*': [4,3,2,1], 7: [3,2,1,2], 4: [2,1,2,3], 1: [1,2,3,4],
        0: [3,2,1,0], 8: [2,1,0,1], 5: [1,0,1,2], 2: [0,1,2,3],
        '#': [4,3,2,1], 9: [3,2,1,2], 6: [2,1,2,3], 3: [1,2,3,4]
    }
    lstate = '*'
    rstate = '#'
    
    for number in numbers:
        if number in [1,4,7]:
            lstate = number
            answer += 'L'
        elif number in [3,6,9]:
            rstate = number
            answer += 'R'
        else:
            lcost = costmap[lstate][ [2,5,8,0].index(number) ]
            rcost = costmap[rstate][ [2,5,8,0].index(number) ]
            if lcost == rcost:
                if hand == 'left':
                    lstate = number
                    answer += 'L'
                else:
                    rstate = number
                    answer += 'R'
            elif lcost < rcost:
                lstate = number
                answer += 'L'
            else:
                rstate = number
                answer += 'R'
    return str(answer)