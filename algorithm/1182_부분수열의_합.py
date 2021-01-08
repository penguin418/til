# 부분순열은 연속부분순열과 다름
# 시간제한 2초
n, s = list(map(int, input().split()))
seq = list(map(int, input().split()))

psum = []
cnt = 0
def dfs(pos):
    global psum, cnt
    if pos == n:
        if  sum(psum) == s and \
            len(psum) > 0: # 하나 이상 선택
            cnt += 1
        return
    else:
        psum.append(seq[pos])
        dfs(pos+1)
        psum.pop()
        dfs(pos+1)
dfs(0)
print(cnt)