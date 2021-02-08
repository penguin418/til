def solution(new_id):
    # 1 소문자로 치환
    new_id = new_id.lower()
    
    # 2 소문자, 숫자, 빼기(-), 밑줄(_), 마침표(.)를 제외한 모든 문자를 제거
    answer = ''
    for char in new_id:
        if char.isalnum() or char in ['-', '_', '.']:
            answer += char

    # 3 마침표(.)가 2번 이상 연속된 부분을 하나의 마침표(.)로 치환
    while '..' in answer:
        answer = answer.replace('..', '.')

    # 4 마침표(.)가 처음이나 끝에 위치한다면 제거
    if answer[0] == '.' and len(answer) > 1:
        answer = answer[1:]
    if answer[-1] == '.':
        answer = answer[0:-1]

    # 5 빈 문자열이라면, new_id에 "a"
    if len(answer) == 0:
        answer = 'a'

    # 6 15개 나머지 제거
    if len(answer) > 15:
        answer = answer[:15]
    # 끝에 위치한 마침표(.) 문자를 제거
    if answer[-1] == '.':
        answer = answer[0:-1]
        
    # 7 길이가 3이 될 때까지 마지막 문자 삽입
    if len(answer) <= 2:
        answer += answer[-1] * (3-len(answer))
    
        
    return answer