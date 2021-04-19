## 병렬 프로그래밍

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

# Executor

자바에서 동기화를 실행하는 방법

태스크와 쓰레드를 관리해준다

## ExecutorService

내부적으로 태스크 큐가 있어서 각 태스크를 관리한다

주요 메서드

- execute(Runnable task)

    Runnable을 바로 실행한다

- submit(Callable<T> task)
- submit(Runnable task)
- shutdown()

    종료한다

- shutdownNow()

디버그용 메서드

- isShutdown()

정적 메서드

- newFixedThreadPool(int size)

    정적 사이즈로 쓰레드 풀을 생성한다

    size개수 만큼 execute하면 됨

    더 많은 경우, 먼저 넣은 쓰레드가 끝나기 전까지 뒤에 넣은 쓰레드는 대기

- newCachedThreadPool()

    가변 사이즈로 쓰레드을 생성한다 

- newSingleThreadExecutor()

    동시에 처리하지 않고 하나씩 처리한다

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