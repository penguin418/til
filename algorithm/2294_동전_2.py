import sys
n, k = map(int, sys.stdin.readline().split())
values = []
for _ in range(n):
    values.append(int(sys.stdin.readline()))

dp = [1234567890 for _ in range(k+1)]
dp[0] = 0
for i in range(n): # i번째 동전 또한 사용하는 방법
    value = values[i]  # 현재 동전의 가치
    for j in range(1, k+1): # j를 만들때 필요한 최소 동전의 개수 구하기
        if j < value:
            continue
        elif j == value:
            dp[j] = 1
        else:
            dp[j] = min(dp[j - value] + 1, dp[j])
        # print(value, j, dp)
if dp[k] == 1234567890:
    print(-1)
else: print(dp[k])
