# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
from collections import defaultdict

def make_mulset(str):
    str = str.lower()
    if len(str)==2:
        return {str:1}
    mulset = defaultdict(int)
    for i in range(len(str)-1):
        if str[i:i+2].isalpha():
            mulset[str[i:i+2]] += 1
    return mulset
def solution(str1, str2):
    mulset1 = make_mulset(str1)
    mulset2 = make_mulset(str2)

    mulset1_keys = set()
    mulset2_keys = set()
    if len(mulset1) > 0:
        mulset1_keys = set(mulset1.keys())
    if len(mulset2) > 0:
        mulset2_keys = set(mulset2.keys())
    print(mulset1_keys, mulset2_keys)
#     intersection = set(mulset1.keys()).intersection(set(mulset2.keys()))
    # print(intersection)
    intersection = mulset1_keys.intersection(mulset2_keys)
    union = mulset1_keys.union(mulset2_keys)
    
    intersection_count = 0
    for _i in intersection:
        intersection_count += min(mulset1[_i], mulset2[_i])
    union_count = 0
    for _u in union:
        union_count += max(mulset1[_u], mulset2[_u])
    print(intersection_count, union_count)
    if union_count != 0:
        return 65536 * intersection_count // union_count
    else:
        return 65536
    