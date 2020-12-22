h = []
for _ in range(9):
    h_input = int(input())
    h.append(h_input)
h.sort()
sumh = sum(h)
sum_spy = sumh - 100
for i in range(9):
    for j in range(i+1, 9):
        if (h[i] + h[j]) == sum_spy:
            answers = [h[a] for a in range(9) if a not in [i,j]]
            for a in sorted(answers):
                print(a)
            exit()
