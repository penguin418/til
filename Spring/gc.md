# 가비지 콜렉터

1. jvm 복습

    운영체제와 독립적으로 jvm위에서 java가 실행

    - 운영체제의 메모리를 낭비하지 않는다.
    - 운영체제가 메모리 해제를 해주지 않으므로 gc성능에 따라 메모리 사용량이 달라짐

    stack

    - heap의 object 타입 데이터에 대한 참조타입(루트)
    - 원시(int, float등) 타입의 데이터
    - 메소드 영역의 지역변수 (scope)
    - 쓰레드 스택

    heap

    - 오브젝트 타입
    - 예외

        Integer, String 등 Immutable 타입의 경우

        ```java
        static void foo(){
        	Member m = new Member("name");
        	bar(m);
        	m; // new name
        }
        static void bar(Member mRef){ m의 copied value (같은 곳 가리킴)
        	mRef.name = "new name"; // m이 가리키던 heap영역이 수정됨
        }
        ```

        ```java
        static void foo2(){
        	Integer i = 1;
        	bar2(i);
        	i;
        }
        static void var2(Integer i){
        	i = 2;
        }
        ```

2. 가비지
    - 주소를 잃어버려 사용할 수 없는(Unreachable) 메모리

        ```java
        String a = "hello"; // 가비지
        a = "world"; // 항상 새로운 string 생성
        ```

- JVM의 영역: Young Generation, Old Generation 영역

    Young Generation: 새롭게 생성한 객체가 위치

    eden 영역: Unreachable 되면 곧 삭제됨, 나머지는 Survivor 1으로 이동(Minor GC)

    S1(Survivor 1) 영역: 카운트1의 영역, 삭제되지 않는 경우 Survivor 2로 이동

    S2(Survivor 2) 영역: 카운트2의 영역, 삭제되지 않는 경우 Old Generation으로 이동됨

    Old Generation: Young Generation 에서 살아남은 객체는 이 영역으로 복제됨

    완전히 가득 찬 경우 GC  수행

    Permanent Generation: 보통 Scoped 메타정보, count가 32정도 될경우 여기로 옴

- GC 단계

    마이너 GC: Young에서 실행됨, Unrechable은 삭제하고 나머지는 카운트 증가, S1, S2, Old로 이동시키는 과정, 간단한 mark sweep이 수행됨, Young만 검사함, 

    - 마이너 GC의 전제

        대부분 객체는 생성후 얼마안되 unreachable

        old가 young을 참조하는 경우는 별로 없다

    메이져 GC: 싹 삭제

    Full GC: Permanent까지 삭제

- 메이저 GC 종류

    Serial GC: Mark Sweep 알고리즘(루트Set에서 부터 살아있는 객체 mark(as Reachable), 나머지 삭제, 이후 힙영역에 정렬), 오래걸릴 수 있음, 메모리가 단편화됨 

    Parallel GC: 멀티쓰레드로 Mark Sweep 수행, 멀티코어에서 유리

    Parallel Old GC: Old영역의 GC 알고리즘이 다름

    CMS GC: 이해가 안됨 ㅋㅋㅋ

    GI GC: Young Old 없이 객체를 빠르게 할당 / GC한다

- GC 수행과정

    Stop-the-World: JVM이 어플리케이션 실행 중지

    이 시간을 줄이는 것을 GC튜닝이라고 함

    GC 수행

- GC 최적화

    객체 할당 횟수 줄이기

    e.g. String builder가 +=보다 좋음

    너무 큰 객체 할당 피하기

    복잡한 참조관계 피하기

    루트 목록 줄이기

1. Metaspace & Heap
    - 자바8부터 적용됨
    -