def get_gcd(a, b):
    if a == 0:
        return b
    if a > b:
        return get_gcd(a%b, b)
    else:
        return get_gcd(b%a, a)
c = int(input())
for _ in range(c):
    nn = list(map(int, input().split()))[1:]
    gcds = 0
    for i in range(len(nn)):
        for j in range(i+1, len(nn)):
            gcds += get_gcd(nn[i], nn[j])
    print(gcds)
