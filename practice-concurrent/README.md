# 자바 동기화 연습

### synchronized

송금과 수수료 지불은 순서대로 한꺼번에 처리되야 하는 작업입니다.

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