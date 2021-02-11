def solution(operations):
    bs = []
    for op in operations:
        com1, com2 = op.split()
        if com1 == 'I':
            insert = int(com2)
            start = 0
            end = len(bs)
            while start < end:
                mid = (start+end)//2
                print(mid, start, end)
                if insert < bs[mid]:
                    end = mid - 1
                elif insert > bs[mid]:
                    start = mid + 1
                elif insert == bs[mid]:
                    start = mid
                    break
            bs.insert(start, insert)
        elif com1 == 'D' and len(bs) > 0:
            if com2 == '1':
                bs.pop()
            else:
                bs.pop(0)
    if len(bs) > 0:
        return [bs[-1], bs[0]]
    else:
        return [0,0]