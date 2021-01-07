# 01_SW역량테스트준비-기초
a, b = map(int, input().split())
def get_gcm(a, b):
    if a < b:
        t = a
        a = b
        b = t
    r = a % b
    if r > 0:
        return get_gcm(r, b)
    return b
print("%d\n%d" % (get_gcm(a,b), a*b/get_gcm(a,b)))
