from datetime import datetime
from queue import PriorityQueue

def solution(lines):
    lines.reverse()
    logs = list(map(lambda k: k.rsplit(' ', 1), lines))

    minimum = 1
    pq = PriorityQueue()
    for et, duration in logs:
        end_time = datetime.strptime(et, "%Y-%m-%d %H:%M:%S.%f").timestamp()
        end_pt = int(float(end_time)*1000)
        start_pt = end_pt - int(float(duration[:-1])*1000) + 1

        pq.put(start_pt * -1)  # 맥스 큐
        while not pq.empty():
            oldest_pt = pq.queue[0] * -1
            if end_pt + 999 < oldest_pt:
                pq.get()
            else:
                break
        if minimum < pq.qsize():
            minimum = pq.qsize()
    return minimum