# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(enroll, referral, sellers, amount):
    earning = {}
    parents = {}
    for i, seller in enumerate(enroll):
        earning[seller] = 0
        parents[seller] = referral[i]
    
    for i, amt in enumerate(amount):
        tax = amt * 100
        worker = sellers[i]
        while tax > 0 and worker != '-':
            amt = tax
            tax = amt // 10
            amt -= tax
            earning[worker] += amt
            worker = parents[worker]
    
    return [earning[worker] for worker in enroll]
            
     