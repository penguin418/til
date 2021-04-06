# dp
from sys import stdin

n = int(stdin.readline())
tps = [ list(map(int,stdin.readline().split())) for _ in range(n)]
tps.insert(0,[0,0])
T = 0
P = 1 # Time, Pay

dp = [0] * (n + 2) # d일날 이후 버는 돈
for d in range(n, 0, -1):
    # 일 못하는 날 제외
    if tps[d][T] > (n-d) + 1:
        tps[d][T] = tps[d][P] = 0
    # d일 작업 수행후 다음 일하기 vs d일 작업 없이 d+1일 작업
    dp[d] = max(dp[d+ tps[d][T]] + tps[d][P], dp[d+1])
print(dp[1])