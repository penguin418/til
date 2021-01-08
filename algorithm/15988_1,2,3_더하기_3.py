# dp
from sys import stdin

t = int(stdin.readline())
ns = [int(stdin.readline()) for _ in range(t)]

dp = [0] * (max(ns) + 1)
dp[0] = 0
dp[1] = 1 # 1
dp[2] = 2 # 11, 2
dp[3] = 4 # 111, 12, 21, 3
dp[4] = 7 # 1(111,12,21,3), 2(11,2), 3(1)
dp[5] = 13 # 1(1111,112,121,13,211,22,31)
           # 2(111,12,21,3)
           # 3(11,2)
for i in range(6, max(ns)+1):
    dp[i] = ( dp[i-1] + dp[i-2] + dp[i-3] ) %  1000000009
for n in ns:
    print(dp[n])