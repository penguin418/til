# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(board, moves):
    
    # top구하기
    side = len(board) # 정사각형 변구하기
    board = [ list(reversed(list(x))) for x in zip(*board)] # 뒤집기
    
    tops = [0] * side
    for i in range(0, side):
        for j in range(side-1,-1,-1):
            if board[i][j] > 0:
                tops[i] = j
                break
    top = 0
    stack = []
    count = 0
    for m in moves:
        pick = 0
        if tops[m-1] > -1:
            pick = board[m-1][tops[m-1]]
            tops[m-1] -= 1
            
            top += 1
            stack.append(pick)
            while top > 1:
                if stack[top-2] != stack[top-1]:
                    break
                del stack[top-2:top]
                top -=2
                count += 1
    return count * 2