n = int(input())

original = n
cnt = 0
while True:
    cnt += 1
    left = n % 10
    if n < 10:
        n = left * 11
    else:
        right = n // 10
        n = left * 10 + (left+right) % 10
    if n == original:
        print(cnt)
        break