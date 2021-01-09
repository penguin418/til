# product 
n, m = list(map(int,input().split()))

nlist = list(range(1, n+2))

def custom_product(arr, start, nxt):
    if start == m:
        print(*arr)
    else:
        for i in range(nxt, n):
            arr[start] = nlist[i]
            custom_product(arr, start+1, i)
custom_product([0]*m, 0, 0)