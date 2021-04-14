# 자바 동기화 연습

### synchronized

송금과 수수료 지불처럼 순서대로 한꺼번에 처리되야 하는 작업을 처리할 때는 syncrhonized 락을 사용합니다

- `고유 락`은 간단한 동기화문제를 쉽게 처리할 수 있는 가장 간단한 방법입니다.
  - `고유 락`은 자바의 모든 객체가 갖는 락으로 `모니터 락` 또는 `뮤텍스`라고 부릅니다
- syncrhonized 는 `고유 락`을 사용하므로 현재 인스턴스 전체에서 적용됩니다

    ```java
    // GoodBank.java
    ...
        public void use() {
            enter();
            // 구조락(structuredLock)은 `고유 락`대상 객체를 명시해야 합니다
            synchronized (this) {
                send();
                payCharge();
            }
            leave();
        }

        public void enter() { logger.info("들어온다"); }
        
        // 고유락은 재 진입(같은 락은 이미 얻었음) 가능합니다
        public synchronized void send() { logger.info("송금한다"); }

        public void payCharge() { logger.info("수수료를 낸다"); }

        public void leave() { logger.info("나간다"); }
    ```

  `구조 락`(structuredLock 또는 블록락) 과 `메소드 락`이 사용되었으며 재진입이 사용되었습니다

  - `구조 락`은 객체 인스턴스 대상으로 유효하며 블럭을 벗어나면 자동으로 해제됩니다
  - `메소드 락`은 메소드 대상으로 유효합니다

### ReenterantLock01 - 기본 사용법

tryLock 처럼 고급 기능이 필요한 경우 `Reenterant Lock`을 사용합니다.

- 예제는 대기하는 방식이지만, 대기하지 않고 다른 작업을 먼저하는 식으로도 사용가능합니다

    ```java
        private void test() {
            try {
            // [필수] try 밖에서 lock 획득
            // tryLock 을 사용해서 대기 가능
            while (!lock.tryLock(0, TimeUnit.MILLISECONDS)) {
                logger.info("대기");
            }
            try {
                cnt += 1;
                logger.info(String.valueOf(cnt));
            } finally {
                    // [필수] finally 안에 unlock
                    lock.unlock();
                }
            // tryLock 사용 시 2중 try-catch 로 인터럽트 보호 필요
            } catch (InterruptedException ignored) {
                logger.warn("인터럽트");
            }
        }
    ```

  - 반드시 finally 안에 unlock 하는 습관을 들여야 합니다
- ReentrantLock은 고유락과 달리 락 객체에 대해서 동기화 됩니다. 두개 이상의 락을 사용할 수 있습니다

    ```java
    public class ReentrantLockTest extends Thread {
        private static final Logger logger = LoggerFactory.getLogger(ReentrantLockTest.class);
        private static final ReentrantLock lock = new ReentrantLock();
        private static int cnt = 0;
    ```