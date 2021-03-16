class MaxHeap(object):
    def __init__(self):
        self.queue = []
    def put(self, n):
        self.queue.append(n)
        index = len(self.queue)-1
        while index >= 0:
            parent = (index-1)//2
            if self.queue[parent] < self.queue[index]:
                self.__swap(parent, index)
            else: break
    def get(self):
        self.
    def __swap(self, i1, i2):
        self.queue[i1], self.queue[i2] = self.queue[i2], self.queue[i1]
        i1, i2 = i2, i1

    def __str__(self):
        return str(self.queue)
mh = MaxHeap()
mh.put(1)
print(mh)
mh.put(2)
print(mh)
mh.put(3)
print(mh)
