# 버전 및 환경

자바11, vert.x 4.1.5

# 개요

non-blocking event-driven 방식의 어플리케이션 프레임워크입니다.

- 등장 배경
    
    초당 많은 요청을 처리 하려면 빠른 처리가 필요하지만, 동시에 많은 연결을 처리 하려면 각 요청을 유한시간 내에 응답하도록 하는 효율적인 스케줄링으로 충분합니다. 만약 동시에 많은 연결이 필요한 경우라면, 멀티 쓰레드는 너무 비용이 비쌉니다. 이때, 단일 비동기 처리를 사용하면, 단일 쓰레드로도 많은 연결을 동시에 처리할 수 있습니다.
    

# 의존성

`vertx` 는 java7 이상이 필요합니다.

verticle을 쉽게 빌드하려면, `vertx-plugin`을 사용하면 좋습니당. 이때, gradle은 6.0부터 지원됩니다.

## 패키지 구성

1. Vert.x Core
    
    HTTP, TCP, File 등을 지원하는 기능으로 구성
    
2. Vertx-platform
    
    배포 및 라이프사이클을 관리하는 기능으로 구성
    

## 용어 및 주요개념

1. Vertx
    
    Verticle를 실행하는 클래스입니다. 각각의 Vert.x Instance(객체)는 자신만의 JVM Instance을 구성하며, Waiting Queue와 (싱글 스레드니깐) 하나의 ELP Thread를 갖습니다. Vert.x Instance내의  일반 Verticle들은 Waiting Queue에서 대기하다가 순서대로(한번에 하나씩) ELP Thread를 점유하여 실행됩니다. 
    
    - Vertx Instance는 VertxOptions, EventBus, WebServer, WebClient, Timer 등을 생성할 때 인자로 사용됩니다.
    - 여러 개의 Vertx 인스턴스를 사용하는 경우
        
        대부분의 프로그램은 하나의 Vertx Instance 만 사용해도 괜찮습니다.  여러 Vertx 인스턴스를 사용할 경우, 서로 다른 설정값을 사용하여 VertxOptions, EventBus, WebServer, WebClient, Timer 를 생성할 수 있습니다. 다만, 이 경우 각각의 Instance에 다른 VertxOption, ... 이 적용되었음을 주의해야 합니다.
        
2. VertxOptions
    
    VertxOptions 는 클러스터, HA, PoolSize 등을 정의하는 옵션입니다. DeploymentOption 과는 다릅니다.
    
3. Verticle
    
    Verticle은 Vert.x의 배치의 기본 단위로, Verticle은 Vert.x Instance 안에서 실행됩니다. Vert.x Instance 는 하나 또는 여러 개의 Verticle로 이뤄질 수 있습니다. 각각의 Verticle은 고유의 클래스 로더를 가질 수 있습니다. 이를 통해 Verticle간 스태틱 멤버과 글로벌 변수를 공유하지 않을 수 있습니다. Verticle 간의 통신은 Event Bus를 사용합니다.
    
4. ELP ( event loop thread)
    - 용어
        
        이벤트 루프를 ELP라고 부르는 것 같습니다.
        
        각각의 이벤트 루프가 독립적인 싱글 쓰레드로 작동하므로 ELP Thread 라는 용어가 보이면 ELP 를 담당하는 싱글 쓰레드라고 보면 됩니다.
        
    
    전체 Verticle이 하나의 ELP 쓰레드에서 실행되므로, ELP는 하나의 작업이 너무 오래 실행되지 않도록 막아야 한다. 이를 위해 BlockedThreadChecker가 존재하며, blocking으로 판단할 경우 Error를 발생시킨다
    
    Blocking 발생 예시
    
    - Thread Lock
    - DB작업
    - Blocking 레거시 코드 호출
    
    Blocking 예외 발생 시, 해결 방법
    
    - VertxOptions::setBlockedThreadCheckInterval() 로 대기시간 연장
    - Vertx::executeBlocking()으로 실행
    - WorkerExecuter::executeBlocking()으로 실행
5. Worker Verticle
    
    일반 Verticle은 ELP에서 순서대로 수행되므로, 긴 작업을 처리하기에는 효율적이지 못하다. Worker Verticle은 이를 극복하기 위해 만든 것으로 Thread Pool에서 비동기로 실행되는 Verticle이다. 
    
    해당 작업만 worker pool로 실행되는 executeBlocking과 달리 작업이 끝나도 종료되지 않고, verticle자체를 workerPool에 넣음
    
    Worker 전용 쓰레드는 기본적으로 20개가 존재.
    
6. 포트 공유
    
    같은 Vertx Instance 는 하나의 포트를 공유할 수 있다
    

## Vert.x 의 구성

Verticle Instance를 생성할 때마다 하나의 eventloop-thread가 할당됨

- EventLoop Thread
    
    Vertx 의 기본 쓰레드 구성
    
    main (자바 기본 쓰레드)
    
    vertx-eventloop-thread0, ...vertx-eventloop-threadn: 코어 수 * 2 만큼 자동 생성되는 쓰레드