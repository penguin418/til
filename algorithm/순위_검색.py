# 검색될 모든 경우의 수 추가
from collections import defaultdict
def hashinfo(cols, depth, res):
    if depth == 4:
        yield res
    else:
        yield from hashinfo(cols, depth+1, res+cols[depth])
        yield from hashinfo(cols, depth+1, res+'-')
        
def solution(infos, querys):
    answer = []
    info_hash = defaultdict(list)
    for info in infos:
        col1, col2, col3, col4, score = info.split()
        cols = [col1, col2, col3, col4]
        score = int(score)
        res = [r for r in hashinfo(cols, 0, '')]
        # 삽입 정렬로 내림차순으로 계산
        for r in res:
            info_hash[r].append(score)
    for ih in info_hash:
        info_hash[ih].sort()
    for q in querys:
        col1, _, col2, _, col3, _, col4, score = q.split()
        score = int(score)
        req = col1+col2+col3+col4
        # 이분 탐색
        if req in info_hash:
            start, end = 0, len(info_hash[req])
            while start<end:
                mid = (start+end)//2
                if score <= info_hash[req][mid]:
                    end = mid
                else:
                    start = mid+1
            answer.append(len(info_hash[req])-start)
        else:
            answer.append(0)
    
    return answer