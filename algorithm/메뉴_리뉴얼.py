# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def solution(orders, courses):
    answer = []
    for i in range(len(orders)):
        orders[i] = ''.join(sorted(list(orders[i])))
    
    for course in courses:
        def combi(num, items, cur, depth):
            if len(cur) == num:
                yield cur
            else:
                for i in range(depth, len(items)):
                    cur.append(items[i])
                    yield from combi(num, items, cur, i+1)
                    cur.pop()
        new_menu_cand = {}
        # 이미 만든 메뉴에서
        for order in orders:
            # 생성한 cand가 등장하는 횟수 계산
            for record in [''.join(c) for c in combi(course, list(order), [], 0)]:
                if record in new_menu_cand.keys(): new_menu_cand[record] += 1
                else: new_menu_cand[record] = 1                        
                new_menu_cand, record
        called = 2
        new_menu = []
        for menu_cand in sorted(new_menu_cand.keys()):
            if new_menu_cand[menu_cand] == called:
                new_menu.append(menu_cand)
            elif new_menu_cand[menu_cand] > called:
                called = new_menu_cand[menu_cand]
                new_menu = [menu_cand]
        answer.extend(new_menu)
    
    return sorted(answer)