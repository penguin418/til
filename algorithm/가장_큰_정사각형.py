# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(board):
    # 보드에서 가장 큰 면적을 젚을 수 있는 정사각형을 찾는 문제
    # 작은 정사각형을 모으면 큰 정사각형이 됨을 이용한다
    # 1 1 
    # 1[2] <- 왼쪽과 위를 포함

    side = len(board)
    for i in range(side): # 행
        for j in range(side): # 열
            possibility = True
            # 내 왼쪽으로 모두 정사각형 인가
            for k in range(i):
                if not board[k][j]:
                    possibility = False
                    break
            # 내 위로 모두 정사각형 인가
            for k in range(j):
                if not board[i][k]:
                    possibility = False
                    break
            if possibility and i > 0 and j > 0:
                if board[i-1][j-1]:
                    board[i][j] = board[i-1][j-1] + 1
    print(board)

solution([[0,1,1,1],[1,1,1,1],[1,1,1,1],[0,0,1,0]])


    # 더 좋은 풀이
    # 1 1 1
    # 1 2 2 <- 오른쪽 끝 2는 이미 4개의 사각형을 포함한다
    # 1 2[3] <- 위로 검사할 필요도 없음 ㅋㅋㅋ

def solution2(board):
    side = len(board)
    for i in range(side): # 행
        for j in range(side): # 열
            if i>0 and j>0 and board[i][j]>0:
                board[i][j] = min(board[i-1][j-1], board[i-1][j], board[i][j-1]) + 1
    print(board)
solution2([[0,1,1,1],[1,1,1,1],[1,1,1,1],[0,0,1,0]])
