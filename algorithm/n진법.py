def nbase(num, n):
    res = []
    while num:
        num, r = divmod(num, n)
        res.append(r)
        if num < n:
            res.append(num)
            return list(reversed(res))
    return [0]

def solution(num, n):
    return(nbase(num, n))