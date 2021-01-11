# n개 중 m개, 중복없이 감소하지않는 순열
from sys import stdin
n, m = list(map(int, stdin.readline().split()))
nums = list(map(int, stdin.readline().split()))

nums.sort()

visit = [0] * n
def combi(arr, start, nxt):
    if start == m:
        print(*arr)
    else:
        prev = -1
        for i in range(nxt, len(nums)):
            if not visit[i] and prev != nums[i]:
                arr[start] = nums[i]
                visit[i] = 1
                combi(arr, start+1, i)
                visit[i] = 0
                prev = nums[i]
combi([0]*m, 0, 0)