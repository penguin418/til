a, b, c = map(int, input().split())
ans1 = (a+b)%c
ans3 = (a%c)*(b%c)%c
print("%d\n%d\n%d\n%d" % (ans1, ans1, ans3, ans3))
