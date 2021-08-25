# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def compress(s, pLen):
    patternPos = 0
    compressed = 0
    # 끝까지 검사
    while patternPos + pLen <= len(s):
    # pLen 만큼의 패턴 추출
        matchCount = 0
        pattern = s[patternPos:patternPos+pLen]
        # pLen 길이만큼 검사
        while patternPos+pLen <= len(s):
            stripe = s[patternPos:patternPos+pLen]
            if stripe == pattern:
                matchCount += 1
                patternPos += pLen
            else:
                break
        # pLen 길이만큼 압출 문자열 생성
        if matchCount == 1:
            compressed += pLen
        elif matchCount > 1:
            compressed += len(str(matchCount)) + pLen
    compressed += len(s[patternPos:])
    return compressed
def solution(s):
    answer = 9876543210
    if len(s) ==1:
        return 1
    for pLen in range(1,len(s)//2+1):
        newAnswer = compress(s, pLen)
        if answer > newAnswer:
            answer = newAnswer
    return answer