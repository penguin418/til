# 집합

`중복`을 허용하지 않는 자료형이다

```python
my_set = set(1,2,3,3) ## {3, 1, 2}
```

`순서`도 존재하지 않는다

```python
my_set[1] ## 순서가 없으므로 인덱싱이 불가능하다
```

원소: 집합을 이루는 요소

# 부분집합

한 `집합`의 `원소`들로만 이루어진 집합

# 곱집합

여러 `집합`의 `원소`들로 이뤄진 `순서쌍`의 집합이다

e.g. A={1,2}, B{a,b} 일때, A X B = {(1,a),(1,b),(2,a),(2,b)}

```python
from itertools import product
list(product(my_set, my_set)) ## [(1, 1), (1, 2) .. (3,3)]
```

```python
def self_product(arr, n): # arr 중 n개 짜리 프로덕트를 만든다
    def _p(cur, nex, dep): # dep은 arr과 길이가 같을때 멈추며 그 전까지 모든 결합을 찾는다
        result = []
        if dep == len(arr): yield cur
        else:
            for i in range(nex, len(arr)):
                yield from _p(cur+arr[i],i,dep+1)
    return _p([],0,len(arr)-n)
```

# 공집합

비어있는 `집합`

# 교집합

여러 `집합`의 공통 `원소`들의 집합

```python
set(1,2,3) & set(1,3,4) ## {1,3}
```

# 합집합

여러 집합의 모든 원소를 포함한 집합

```python
set(1,2,3) | set(1,3,4) ## {1,2,3,4}
```

# 멱집합

어떤 `집합`의 `부분집합`을 모은 집합

순서와 상관없는 집합의 특성을 이용하여 모든 `조합`을 구하면 된다

```python
from itertools import combinations
for i in range(len(my_set)+1):
	result.extend( list(combinations(my_set, 1)) )
```

# 차집합

어떤 `집합`에서 다른 집합의 원소를 제외하는 연산 

```python
set(1,2,3) - set(1,3,4) ## {2}
```

# 순열

n개의 `원소`를 골라 `순서`를 고려해 나열한 `경우의 수`

```python
from itertools import permutations
list(permutations(my_set, 2)) ## [(1,2),(2,1),(2,3),(3,2),(1,3),(3,1)]
```

```python
def permutations(arr, n):
    def _p(cur, dep, visit): # 모든 순서를 찾을거니까 항상 처음부터 시작한다
        result = []
        if dep == len(arr):
            yield cur
        else:
            for i in range(0, len(arr)): 
                if visit[i]: continue
                visit[i] = 1 # 이번 탐색에서 방문한 노드를 탐색하지 않는다
                yield from _p(cur + [arr[i]],dep+1, visit)
                visit[i] = 0
    
    return _p([],len(arr)-n, [0]*len(arr))
```

# 조합

n개의 `원소`를 `순서에 관계없이` r개의 배열로 나타내는 방법이다

```python
from itertools import combinations
list(combintations(my_set, 2)) ## [(1,2),(2,3),(3,1)]
```

```python
def combi(arr, n): # arr에서 n개 짜리 조합을 찾는다
    def _c(cur, nex, dep, visit):
        result = []
        if dep == len(arr):
            yield cur
        else:
            for i in range(nex, len(arr)): # 방문한 노드를 절대 탐색하지 않는다
                if visit[i]: continue
                visit[i] = 1
                yield from _c(cur + [arr[i]],i+1,dep+1, visit)
                visit[i] = 0
    
    return _c([],0,len(arr)-n, [0]*len(arr))
```

# 힙

힙은  `우선순위큐`를 구현하기 위해 특별한 `삽입`, `삭제` 규칙을 갖는 `완전 이진트리`이다

# 순회

`그래프`에서 모든 정점을 방문하는 방법이다

# 경로

`그래프`에서 `정점`을 중복하여 지나지 않는 `부분 그래프`이다

# 완전 그래프

모든 노드가 인접한 `그래프`이다

차수의 개수 = 정점의 개수 -1

# 부분 그래프

일부 정점과 간선만 있는 `그래프`

# 그래프

`정점`과 `간선`으로 이루어진 자료구조이다

`리스트`와 `행렬`로 나타낼 수 있다

- `인접 리스트`

    그래프의 연결 관계를 리스트로 표현한다

    연결된 간선만 표현하므로 저장공간이 줄어든다

    간선이 거의 없는 `희소 그래프`는 주로 리스트로 표현된다

- `인접 행렬`

    그래프의 연결 관계를 행렬(보통 2차원)으로 표현한다

    시간복잡도가 O(1)이다

    간접적으로 연결된 정점을 연결할때 추가적인 저장공간이 필요하지 않다

    방향 그래프의 경우, 행 → 열로 가중치, 또는 연결상태를 표현할 수 있다

    간선이 최대 개수에 가까운 `밀접 그래프`는 주로 행렬로 나타낸다

차수: 한 정점에 이어진 간선의 개수

사이클: 

# 트리

사이클이 없는 `그래프`이다 

서로 다른 두 `정점`은 하나의 `경로`로만 연결된다

# 이진트리

이진트리는