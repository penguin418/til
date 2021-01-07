# 02_SW역량테스트준비-기초
e, s, m = map(int, input().split())
ee, ss, mm, i = 0,0,0,0
while 1:
    ee = ee+1 if ee+1 < 16 else 1
    ss = ss+1 if ss+1 < 29 else 1
    mm = mm+1 if mm+1 < 20 else 1
    i += 1
    if ee == e and ss == s and mm == m: break
print(i)
