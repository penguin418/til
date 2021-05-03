참고: [https://youtu.be/mTGdtC9f4EU](https://youtu.be/mTGdtC9f4EU) 와 [http://tutorials.jenkov.com/](http://tutorials.jenkov.com/)

## 병렬 프로그래밍

목차 (계획 포함)

- 병행성과 병렬성
- 쓰레드 기본
- 쓰레드 지원 컬렉션
    - 데이터 전달(단방향)
    - 데이터 전달(양방향)
- Executors
- low-level sync
    - Lock, Condition
- high-level sync
    - cyclicBarrier
    - CountDownLatch
    - Semaphore
    - Exchanger
- Atomic operation

### 병행성(Concurrecny)과 병렬성(Parallelism)

- 병행성

    모든 태스크가 동시에 실행되는 것처럼(cpu를 나눠써서) 보이는 것

    방법

    - 시분할(cpu상)
    - 생산 소비 패턴
- 병렬성

    실제로(다른 cpu에서) 동시에 처리되는 것

    - 암달의 법칙

    방법

    - 데이터 병렬: 동일 연산을 나누어 실행
    - 태스크 병렬: 여러 작업을 나누어 실행

    관련 문제:

### 쓰레드

쓰레드 라이프 사이클

- `new` =start()⇒ `runnable` =run() ⇒ `running`
- `new` ⇒ `dead`
- `runnig` ⇒ `dead`
- `runnig` =sleep(), wait()⇒ `blocking` ⇒ `dead`

쓰레드 클래스 상속받기

주요 메소드

- setPriority(int newPriority)

    쓰레드 실행 전에만 설정 가능!

    숫자 높을 수록 우선, 1~10

- getPriority() // 기본값 5

디버깅용 메소드

- setName

    ~~아주 좋다~~이름을 설정한다

static 메소드

- sleep(long millis)

    millis초만큼 대기

    사용 시 InterruptedException

    ```java
    try{
       sleep(1000);
    catch(InterruptedException ignored){ }
    ```

- notify()

    1개 깨움, (@deprecated resume을 대체한다)

- notifyAll()

    모두를 깨움

- wait()

    대기, (@deprecated suspend를 대체한다)

- join(), join(long millis), join(long millis, int nanos)

    작업이 종료되거나 지정시간이 지나면 쓰레드를 종료후 호출쓰레드로 돌아온다

- yield()

    다른 쓰레드에게 양보 후 Runnable로 돌아감

Runnable 인터페이스 구현하기

- 다중 상속시 사용, Thread 상속이 비싸서 사용
- 이 경우 Thread.sleep 등으로 사용

### 쓰레드 그룹

보안상의 이유로 도입되었으며 다른 쓰레드 그룹을 변경할 수 없도록 하여 보안을 지킨다

모든 쓰레드는 쓰레드 그룹에 들어있어야 하며 기본 쓰레드 그룹은 main이고 main의 하위 쓰레드그룹만 생성할 수 있다

- finalizer 쓰레드는 system 쓰레드에 속한다

생성자

- Thread(ThreadGroup group, String name)
- Thread(ThreadGroup group, Runnable target)

주요메소드

- getThreadGroup()

    현재 쓰레드그룹 반환

- activeCount()

    현재 그룹에서 작업중인 쓰레드 개수

- interrupt()

    현재 그룹의 모든 쓰레드에 인터럽트

### 데몬쓰레드

일반 쓰레드를 보조하는 쓰레드를 구현할때 사용되며, 일반 쓰레드가 모두 종료되면 함께 종료된다

화면 갱신, 자동저장 등의 역할을 수행하기 위해 만들어졌다

메소드

- setDaemon(boolean on)

    쓰레드 시작전에만 설정가능하다

    true로 지정하면 데몬이 된다

테스트용 메소드

- isDaemon()

    현재 쓰레드가 데몬인지 확인한다

    - 데몬이 생성한 쓰레드도 데몬이 된다

예제

```java
class Daemon implements Runnable{
    public void run() {
        while(true){
            if(condition){/*...작업...*/}
            Thread.sleep(1000);
        }
    }
}
    // main
    Thread t = new Daemon1();
    t.setDaemon(true);
    t.start();
    
    return; // main종료 시 데몬도 함께 종료됨
}
```

# Executors

쓰레드 풀을 관리하기 위한 라이브러리로 자바5에서 추가됬다

리턴값이 없는 Runnable 과 리턴값이 존재하는 Callable을 관리할 수 있다

자바 8 이후로는 FunctionalInterface가 도입되었다

- Runnable

    ```java
    @FunctionalInterface
    public interface Runnable {
        public abstract void run();
    }
    ```

- Callable

    ```java
    @FunctionalInterface
    public interface Callable<V> {
        V call() throws Exception;
    }
    ```

쓰레드 풀 생명주기 관리를 지원해준다

- 쓰레드 생성
- 쓰레드 동작
- 처리 완료된 쓰레드 제거

## ExecutorService

내부적으로 태스크 큐가 있어서 각 태스크를 관리한다

주요 메서드

- execute(Runnable task)

    Runnable(리턴값이 없는 쓰레드)을 바로 실행한다

    execute 는 `runnable` 만 실행시킬 수 있다

    만약, 에러가 발생 시 실행 쓰레드는 쓰레드 풀에서 바로 삭제된다

    ExecutorService가 상속받는 Executor 인터페이스에 정의되어있다

    ```java
    public interface Executor {
        void execute(Runnable command);
    }
    ```

- submit(Runnable task)

    ExecutorService 에 추가한다. 

    이후, 내부 스케줄링에 의해 적절하게 수행된다

    만약, 에러가 발생 시 에러 출력 후, 실행 쓰레드는 쓰레드 풀 끝에 다시 추가된다

- submit(Runnable task, V result)

    Runnable은 값을 반환하지 않는 만큼,

    완료 시 반환 할 값( 상수 )을 result에 추가하려 호출 할 수 있다

    Future<V> 타입을 통해 리턴값을 반환할 수 있다

- submit(Callable<T> task)

    Callable은 값을 반환하는 만큼,

    Future<V> 타입을 통해 리턴값을 반환할 수 있다

- shutdown()

    종료한다

    이때, awaitTermination(long timeout, TimeUnit unit) 를 추가로 호출하여 전체 작업이 끝날 때 까지 기다릴 수 있다

    ```java
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    while (true) {
        executorService.execute(new TaskA());
    }
    executorService.shutdown(); // 종료하기

    try {
    		boolean finished; // timeout 덕분에 정상, 비정상 종료 여부를 파악할 수 있다
        finished = executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        if (!finished)
            // TODO: 아직 안 끝났음
    } catch (InterruptedException ignored) {}
    // finished 의 기본값을 false로 두고 finally에서 처리하는 것도 괜찮을 것 같다
    ```

- shutdownNow()
- invokeAll()

    다수의 태스크를 한번에 실행할 수 있다

    ```java
    List<Callable<Integer>> taskList = ...
    List<Future<Integer>> resultList = executorService.invokeAll( taskList );
    ```

- invokeAny()

    다수의 태스크를 실행하지만, 처음으로 성공한 태스크의 결과만 리턴하고 나머지는 취소한다

    ```java
    List<Callable<Integer>> taskList = ...
    Integer result = executorService.invokeAny( taskList );
    ```

디버그용 메서드

- isShutdown()

정적 메서드

- newFixedThreadPool(int size)

    정적 사이즈로 쓰레드 풀을 생성한다

    size개수 만큼 execute하면 됨

    더 많은 경우, 먼저 넣은 쓰레드가 끝나기 전까지 뒤에 넣은 쓰레드는 대기

- newCachedThreadPool()

    가변 사이즈로 쓰레드풀을 생성한다 

    60초 간 작업이 없는 경우, 풀에서 제거한다

- newSingleThreadExecutor()

    동시에 처리하지 않고 하나씩 처리한다

    TaskPool 이 필요한 경우 사용된다

    - TaskPool: 태스크 자료구조

참고

[https://www.geeksforgeeks.org/difference-between-executorservice-execute-and-submit-method-in-java/](https://www.geeksforgeeks.org/difference-between-executorservice-execute-and-submit-method-in-java/)

# Future

Executable의 반환값을 받을 수 있는 객체로 blocking과 time-wait 을 모두 사용하는 get() 메소드를 지원한다

- get()

    blocking 메소드로, 실행을 기다리고 결과를 반환한다

    ```java
    Future<Integer> result = executorService.submit( ... );
    int resultValue = result.get(); // 실행 완료까지 block된다
    ```

- get(long timeout, TimeUnit unit)

    대기시간 만큼 기다렸다가 타임아웃을 일으킨다

    TimeoutException 을 위해 try-catch 를 준비할 것

- cance(boolean mayInterruptIfRunning)

    mayInterruptIfRunning에 따라 실행중인 작업인 경우 취소할지(true) 정한다

    시작 안된 경우 당연히 취소되고, 이미 끝난경우 취소할 수 없다

- isCanclled()

    취소가 성공했는지 확인한다

- isDone()

    이미 작업이 완료되었는지 확인한다

# 동기화 제어

멀티 쓰레드 상에서 동기화를 제어하는 방법이다

쓰레드 안정성은 두가지 측면에서 볼 수 있다

- 실행제어: 서로 다른 쓰레드에서 동시에 실행될 수 있나?

    자원의 동기화

    - 임계영역

        어떤 자원에 하나의 쓰레드만 접근 할 수 있다

    - 뮤텍스

        공유자원에 락을 걸고 사용한다

        굳이 같은 프로세스가 아니더라도 락을 획득하면 사용 가능한 개념, 하지만 JVM이 멀티프로세싱을 지원하지 않으므로 lock의 여부에 달려있음

        e.g. synchronized, ReentrantLock(뮤텍스)

    - 세마포어

        한정된 개수의 자원을 공유

    실행 순서의 동기화

    - 이벤트

        완료를 알린다

    - 폴링

        특정 시간이 되면 쓰레드를 깨운다 

- 메모리 가시성: 한 쓰레드에서 수정한 결과를 다른 쓰레드에서 읽을 수 있나?

    cpu가 변수를 읽고 수정할 때, 메인메모리 혹흔 캐시에서 레지스터로 값을 가져와 수정하기 때문에, 쓰레드는 주 메모리의 복사본을 얻어 작업하고 나중에 덮어쓰는 2가지 작업이 발생한다. 따라서 읽은 값이 다를 수 있다

    해결책은 Voltile, Atomic을 통해 제공된다

## Synchronized

동기화 메소드/블록을 사용하는 방법이다

### 인스턴스 메소드 ( Synchronized Method )

```java
public synchronized void syncMethodOne(){
	try{
		// 동기화 되는 구역
	}catch(InterruptException e){
		throws new RuntimeException("update 실패");
	}
}
```

- lock범위: 메소드를 포함하는 인스턴스이다

    오직 하나의 인스턴스 동기화 메소드만이 동시에 실행 가능하다

    한 쓰레드에서 syncMethodTwo는 syncMethodOne과 동시에 실행됨을 방지한다

### 스태틱 메소드 ( Static Synchronized Method )

```java
public static synchronized void staticSyncMethod(){
    // ... 인스턴스 메소드와 동일
}
```

- 락범위: 메소드가 정의된 클래스 객체이다

    오직 하나의 객체만이 실행가능하다

### 인스턴스 메소드 코드블록 ( Synchronized { block } )

```java
public void method(){
    // 동기화가 필요없는 구역 !
    synchronized( this /* 모니터 객체 */ ){
        try{
            // 동기화 되는 구역
        }catch(InterruptException e){
            throws new RuntimeException("update 실패");
        }
    }
}
```

- 모니터 객체: 동기화 블록에서 동기화의 기준이 되는 객체이다

    따라서 모니터 객체에 `this`를 넣으면 synchronized method와 동일한 기능을 수행하게 된다

    동기화된 블록은 오직 한 쓰레드만이 실행 가능하다

### 스태틱 메소드 코드블록 ( Synchronized { block } )

```java
public static synchronized void staticSyncMethod(){
	// ... 인스턴스 메소드 코드블록과 동일
}
```

## Volatile

단일 변수에 대한 읽기 동기화(읽기 최신화)를 보장하는 방법이다

- 메인메모리 읽기 쓰기를 강제한다

     → 값 불일치 문제가 일어나지 않는다

- `리오더링`에서 제외시켜 코드가 작성된 순서대로 읽기 쓰기를 강제한다

    → 값 쓰기, 값 읽기 순서가 강제된다

    - `리오더링`: 런타임시 좀더 빠르게 실행되도록 컴파일타임에 코드를 최적화하는 작업이다

    ### Volatile

    ```java
    volatile int counter = 0;
    ```

쓰기 쓰레드1, 읽기 쓰레드 n인 상황에 적합하다

- volatile은 메인 메모리에서 직접 읽고, 직접 쓰는 것만을 강제하므로, 두 쓰레드에서 동시에 읽고 쓰는 작업을 수행하면 메인메모리에 담긴 값을 더럽히는 것을 막을 방법이 없다

## CAS

단일 변수에 대한 동기화 방법으로 읽기, 쓰기 모두에 대해 안전하다

CAS 알고리즘을 구현한 Atomic** (java.util.concurrent.Atomic) 클래스를 통해 지원한다

ConcurrentHashMap에서도 값을 확인할때 사용되며, 실제 변경시에는 Synchronized를 사용한다

### AtomicInteger

```java
...
AtomicInteger atomicInt = new AtomicInteger(55); //기본값 0
atomicInt.get(); // 55
atomicInt.set(10);
atomicInt.getAndSet(20); // 10
atomicInt.compareAndSet(20, 14); // true
atomicInt.compareAndSet(20, 15); // false, 값도 안바뀜
```

### ConcurrentHashMap

노드를 volatile로 관리하며 수정 시 compareAndSwapObject메소드를 사용합니다

CompareAndSwap

- `CompareAndSwap`:

    ```c
    boolean cas(int address,int expected,int update) {
    	int original = *address;
    	if (*address == expected)
    		*address = update; // 동일하면 업데이트
    	return original;
    } // 하드웨어에서 지원하는 원자적 연산
    ```

- `CompareAndSet`

    ```c
    boolean cas(int address,int expected,int update) {
    	if (*address != expected) return false; // 기댓값과 다르면 false
    	*address = update; // 동일하면 업데이트하고, true
    	return true;
    } // 하드웨어에서 지원하는 원자적 연산
    ```

ABA문제

CAS 알고리즘을 사용하는 락프리 알고리즘이 수행될 때, 메모리 재사용으로 인해 발생하는 문제입니다

1. 주소 + 버전
2. SC명령
3. Reference Counter

### synchronized

송금과 수수료 지불처럼 순서대로 한꺼번에 처리되야 하는 작업을 처리할 때는 syncrhonized 락을 사용합니다

- `고유 락`은 간단한 동기화문제를 쉽게 처리할 수 있는 가장 간단한 방법입니다.
    - `고유 락`은 자바의 모든 객체가 갖는 락으로 `모니터 락` 또는 `뮤텍스`라고 부릅니다

### ReenterantLock

인터럽트 시 해제 처럼 고급 기능이 필요한 경우 `Reenterant Lock`을 사용합니다.

`고유락`과 달리 시작과 끝을 명시하기 때문에 `명시 락`이라고도 부릅니다

추가적으로 다음의 경우 사용하면 좋습니다

- 타임아웃이 필요한 경우(time-lock-waits)

    e.g. 정해진 시간내 처리해야 하는 경우

- 인터럽트가 필요한 경우(interruptible-lock-wait)
- 블록 단위가 아닌 락(non-block-structured lock)
- 다 조건 락(multiple condition lock)
- 락 폴링이 필요한 경우(lock-polling)
- 출처: [https://stackoverflow.com/questions/11821801/why-use-a-reentrantlock-if-one-can-use-synchronizedthis](https://stackoverflow.com/questions/11821801/why-use-a-reentrantlock-if-one-can-use-synchronizedthis)

주요 메소드

- lock()

    락을 획득/대기 할때 사용

    획득가능 한 경우 holdCount=1 및 리턴

- unlock()

    holdCount=0 및 락 해제

    (유의) 현재 쓰레드가 락의 주인이 아닌경우 예외발생(IllegalMonitorStateException)

- tryLock(Long timeout, TimeUnit unit)

    timeout만큼 락을 시도하다가 안되면 대기취소

- lockInterruptibly()

    interrupt가 걸려있는 경우 발생시킨다

- await()

    쓰레드의 wait()

- signal()

    쓰레드의 notify()

- signallAll()

    쓰레드의 notifyAll()

테스트용 메소드

- getHoldCount
- isHeldByCurrentThread

### Blocking Queue

block 기반의 thread-safe 단방향 메시지 큐 인터페이스

- null 삽입 불가 (`NullPointerException`던짐)

    null 필요 시 표준 규약을 만들고, 예외처리해야 함

- capacity bound
    - 가득 차면(remainingCapacity() 를 통해 확인) 더 이상 put되지 않고 block 된다
    - queue가 비면 더 이상 take하지 못하고 block 된다
- 원자성을 보장하지만, bulk-operations 는 예외

    addAll, containsAll, retainAll 등

- close, shutdown없음

    미리 표준 규약을 만들고 데이터 전송 종료를 consumer가 판단하도록 해야 함

- 4가지 전략 지원
    1. throw exception: 실패 시 즉시 exception 발생
    2. special value: 실패 시 null 혹은 결과에 따라 false /true 리턴
    3. block: 가능할때까지 대기
    4. timeout: 주어진 시간동안만 block
- 다양한 구현 제공
    - ArrayBlockingQueue

        고정 배열을 사용한 큐, 크기 변경 불가

        - ArrayBlockingQueue(int capacity)

        공정성 보장 가능 (이 경우 fifo 순서로만 작동)

        - ArrayBlockingQueue(int capacity, boolean fair)
    - LinkedBlockingQueue

        capacity 내에서 node가 동적으로 생성되는 큐

        head와 tail로 lock이 분리되어 동시에 삽입/삭제가 가능하므로 좀더 높은 throghput을 지원

        capacity를 지정하지 않는 경우 Integer.MAX_VALUE 가 기본값

    - PriorityBlockingQueue

        용량제한 없는 큐

        - SynchronousQueue()
        - SynchronousQueue(boolean fair)

            마찬가지로 fair=true일때, fifo 순서로만 작동

    - DelayQueue

        delay 시간이 지난 요소만 poll이 가능한 큐

        PriorityBlockingQueue를 상속받아 구현

        - 마찬가지로 용량제한이 없다
        - 큐 내부 순서는 delay가 지난 것 중 오래된 순서
        - ⭐큐가 비었을 경우 null을 리턴한다

        Delayed 인터페이스를 상속받은 객체만 삽입할 수 있다

        ```java
        public interface Delayed extends Comparable<Delayed> {
            long getDelay(TimeUnit unit);
        }
        ```

        getDelay를 구현하는 방법

        ```java
        public class DelayTask implements Delayed {
          private final long delayTime;
        	private long executionTime;
        	
        	// delayTime 만큼 미룸
        	public DelayTask(){
        		this.executionTime = System.currentTimeMillis() + delayTime;
        	}

        	// executionTime 과의 차이를 반환
          public long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(
        			executionTime - System.currentTimeMillis(), 
        			TimeUnit.MILLISECONDS
        		);
          }

          
          @Override
          public int compareTo(Delayed o) {
            long diff = getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
            return (int)diff;
          }
        }
        ```

    - SynchronousQueue

        put과 take가 항상 동시에 일어나는 큐

    참고: [https://stackoverflow.com/questions/35967792/when-to-prefer-linkedblockingqueue-over-arrayblockingqueue](https://stackoverflow.com/questions/35967792/when-to-prefer-linkedblockingqueue-over-arrayblockingqueue)

전략

Throw Exception 전략

- add(E o) / remove(Object o)

    즉시 삽입, 삭제가 불가능한 경우  예외(`IllegalStateException`)를 던진다

- addAll(Collection<? extends E> c) / removeAll()

    즉시 삽입, 삭제가 불가능한 경우  예외(`IllegalStateException`)를 던진다

- element()

    첫번째 요소를 즉시 출력만 한다

    만약 없는 경우 예외(`IllegalStateException`)를 던진다

Special Value 전략

- offer(E o), poll()

    즉시 삽입, 삭제가 불가능한 경우 `false`를, 성공 시 `true`를 반환한다

- peek()

    첫번째 요소를 즉시 출력만 한다

    만약 없는 경우 `null`을 반환한다

Blocks전략

- put(E o), take()

    즉시 삽입, 삭제가 가능한 경우 삽입, 출력한다

    실패 시 block

    ```java
    // Main 클래스
        @AllArgsConstructor
        private static class Producer implements Runnable {
            private final BlockingQueue<Integer> queue;

            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) queue.put(i);
                } catch (InterruptedException ignored) {
                }
            }
        }
        @AllArgsConstructor
        private static class Consumer implements Runnable {
            private final BlockingQueue<Integer> queue;

            @Override
            public void run() {
                try {
                    int i=0;
                    while(i != 4){
                        i = queue.take();
                        System.out.println(i);
                    }
                } catch (InterruptedException ignored) {
                }
            }
        }
    ```

Timeout 전략

- offer(E o, long timeout, TimeUnit unit), poll(long timeout, TimeUnit unit)

    가능한 경우 제공하고, 꺼낸다

    시간내 삽입, 삭제가 출력이 불가능한 경우 즉시 예외(`IllegalStateException`)를 발생시킨다

벌크 메소드

- add(E o), remove(E o), element(

위의 예시 실행 

```java
// Main 의 main 메서드
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        BlockingQueue<Integer> bq = new LinkedBlockingDeque();
        executorService.submit(new Producer(bq));
        executorService.submit(new Consumer(bq));
        executorService.shutdown();
    }
```