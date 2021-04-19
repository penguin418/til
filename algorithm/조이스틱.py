from collections import deque, defaultdict
def solution(name):
    name = list(name)
    
    # 변경비용
    def get_single_cost(target):
        alpha = list('ABCDEFGHIJKLMNOPQRSTUVWXYZA')
        cost_front = alpha.index(target)-0
        cost_back = 26 - alpha.index(target)
        return min(cost_front, cost_back)
    
    costs = [0] * len(name)
    for i, n in enumerate(name):
        costs[i] = get_single_cost(n)

    # 이동 비용
    left_paths = dict()
    right_paths = dict()
    next_one = dict()
    before = 0
    for i, c in enumerate(costs):
        if c:
            left_paths[i] = i
            right_paths[i] = len(name)-i
            next_one[before] = i
            before = i
    next_one[before] = 0
    left_paths[0] = 0
    right_paths[0] = 0
    print(left_paths)
    print(right_paths)
    print(next_one)
    # 123__67 방문시, 1[1]2[1]3[3]6[1]7 ... 로 접근가능
    min_path = 1234567890
    for i, c in enumerate(costs):
        if c:
            lpath = left_paths[i] * 2 + right_paths[next_one[i]]
            rpath = left_paths[i] + right_paths[next_one[i]]*2
            min_path = min(min_path, lpath, rpath)
            print(lpath,rpath, i, next_one[i])
    if min_path == 1234567890:
        min_path = 0
    return min_path + sum(costs)