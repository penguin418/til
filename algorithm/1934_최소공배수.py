def get_gcm(a, b):
    if a < b:
        t = a
        a = b
        b = t
    r = a % b
    if r > 0:
        return get_gcm(r, b)
    return b
n = int(input())
for _ in range(n):
    a, b = map(int, input().split())
    print(a*b//get_gcm(a,b))
