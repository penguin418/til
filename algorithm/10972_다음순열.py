N = int(input())
s = list(map(int,input().split()))
find = False
for i in range(N-1,0,-1):
    if s[i-1] < s[i]:
        for j in range(N-1,0,-1):
            if s[i-1] < s[j]:
                s[i-1],s[j]=s[j],s[i-1]
                s=s[:i]+sorted(s[i:])
                find=True
                break
    if find:
        print(*s)
        break
if not find:
    print(-1)
