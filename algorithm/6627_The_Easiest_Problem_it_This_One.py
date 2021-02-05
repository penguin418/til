import sys
while True:
    n = int(sys.stdin.readline())
    s = sum(map(int, str(n)))
    if n == 0:
        break
    p = 10
    while True:
        p+=1
        if s == sum(map(int, str(n*p))):
            print(p)
            break
    