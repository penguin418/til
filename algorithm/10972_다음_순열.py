# next permutation 
from sys import stdin
len = int(stdin.readline())
seq = list(map(int, stdin.readline().split()))

found = False
for i in range(len-1, 0, -1):
    if seq[i-1] < seq[i]:
        found = True
        for j in range(len-1, i-1, -1):
            if seq[j] > seq[i-1]:
                seq.insert(i-1, seq.pop(j))
                seq.insert(j, seq.pop(i))
                seq[i:len] = seq[i:len][::-1]
                print(* seq)
                break
        break
if not found:
    print(-1)