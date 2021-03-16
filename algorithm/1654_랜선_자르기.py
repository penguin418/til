import sys
# 이미가진 랜선 k개, 필요한 랜선 N개
k, n = map(int, sys.stdin.readline().split())
# k 개의 랜선
lines = []
for _ in range(k): lines.append(int(sys.stdin.readline()))
# if k == 1 and lines[0] == 1:
#     print(1)
#     sys.exit(0)

answer = 0
left = 1 # 마지노선
right = max(lines) # 모두 1씩 잘름
while left <= right:
    mid = (left+right)//2
    cnt = sum(list(map(lambda x: x//mid, lines)))
    # print(mid, cnt, list(map(lambda x: x//mid, lines)))
    if cnt >= n:
        answer = max(answer, mid)
        left = mid + 1
    else:
        right = mid - 1
print(answer)   