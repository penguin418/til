from sys import stdin
from itertools import combinations

# while True:
#     inputs = list(map(int, stdin.readline().split()))
#     if inputs[0] == 0:
#         break
#     selected = inputs[1:]
#     for p in combinations(selected, 6):
#         print(* p)
#     print()

while True:
    inputs = list(map(int, stdin.readline().split()))
    if inputs[0] == 0:
        break
    selected = inputs[1:]

    def combi(picks, picked):
        if len(picks) == 6:
            print(* picks)
        for nxt in range(picked, len(selected)):
            picks.append(selected[nxt])
            combi(picks, nxt+1)
            picks.pop()
    combi([], 0)
    print()


