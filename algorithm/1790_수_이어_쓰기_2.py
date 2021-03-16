import sys

def calc(n):
    length = 0
    num_len = 1
    # 1(num_len=1) -> 10(num_len=2) -> 100
    while n >= 10**num_len:
        # e.g. num_len=2일때, 2자리수 * 10 * 9 개
        length += num_len * (10 ** (num_len-1)) * 9
        num_len += 1
    # e.g. 391 = 1 ~ 99 + 100 ~ 291 -> 292개의 3자리 수
    return length + (n - 10**(num_len-1) + 1) * num_len

n, nth = list(map(int, sys.stdin.readline().split()))
if nth > calc(n):
    print(-1)
    sys.exit(0)

left = 0
right = n
index = 0
while left <= right:
    mid = (left+right)//2
    index = calc(mid)
    if index < nth:
        left = mid+1
    else:
        right = mid-1
print( str(left)[nth - calc(right)-1] )
