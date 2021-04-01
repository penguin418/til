# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
import collections
def solution(participant, completion):
    answer = ''
    
    part_dict = collections.Counter(participant)
    comp_dict = collections.Counter(completion)
    
    failed = part_dict - comp_dict
    
    return list(failed.keys())[0]