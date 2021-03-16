def solution(s):
    answer = True
    
    p = s.lower().count('p')
    y = s.lower().count('y')
    print(p, y)
    return p == y
    