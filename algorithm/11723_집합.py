from sys import stdin
cases = int(stdin.readline())
s = set()
for i in range(cases):
    command = stdin.readline()
    if command == 'all\n':
        s = set([e for e in range(1,21)])
        continue
    elif command == 'empty\n': 
        s = set()
        continue

    command, n = command.split()
    n = int(n)
    if command == 'add': s.add(n)
    elif command == 'remove': 
        try: s.remove(n)
        except: pass
    elif command == 'check': print(1) if n in s else print(0)
    elif command == 'toggle': s.remove(n) if n in s else s.add(n)  
