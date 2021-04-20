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