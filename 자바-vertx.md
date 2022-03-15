# 버전 및 환경

자바11, vert.x 4.1.5

# 개요

non-blocking event-driven 방식의 어플리케이션 프레임워크입니다.

- 등장 배경
    
    초당 많은 요청을 처리 하려면 빠른 처리가 필요하지만, 동시에 많은 연결을 처리 하려면 각 요청을 유한시간 내에 응답하도록 하는 효율적인 스케줄링으로 충분합니다. 만약 동시에 많은 연결이 필요한 경우라면, 멀티 쓰레드는 각 요청이 끝날 때까지 쓰레드를 점유하기 때문에 비용이 너무 비쌉니다. 이때, 단일 비동기 처리를 사용하면, 단일 쓰레드로도 많은 연결을 동시에 처리할 수 있습니다. 
    
    Vert.X의 reactor 패턴은 위의 아이디어를 잘 구현할 수 있습니다. reactor 패턴은 reactive 프로그래밍을 하는 방법인데, 외부의 데이터 스트림을 처리하는 verticle 과 실제로 (오래걸리는) 일을 하는 verticle 을 분리하는 패턴입니다. 이를 통해, 외부 데이터 스트림을 입력받는 verticle은 밀려드는 요청을 놓치지 않고 큐에 배정할 수 있습니다.
    
- 장점
    
    vert.x는 쉽습니다. vert.x의 가장 작은 단위인 verticle에서 제공하는 가장 중요한 두가지 메서드만 존재하는 데, start와 stop 입니다. start동작과 stop 동작만 구현하면 됩니다. Vert.X의 가장 작은 단위인 Verticle은 메모리에 올라가면 Instance가 되고, start() 메서드가 실행될 때, 이벤트 루프를 할당받습니다. 각 Instance는 처음 할당받은 이벤트루프에서만 실행되며, 이를 통해 Instance는 마치 싱글 쓰레드처럼 실행되면서 내부에선 상태가 보장됩니다.
    
    부하가 높은 경우, 인스턴스를 여러개 띄우면 됩니다. Vert.x는 인스턴스를 여러개 복사하여 멀티쓰레드를 구현합니다. 앞서 밝히듯 각 Instance는 상태가 보장되므로, 각 Instance 내부에서 lock이나, synchronized 같은 고민을 할 필요가 없습니다. Instance 간에는 Shared Data 라는 Concurrent Hash map을 공유하여 데이터를 공유할 수 있습니다. 또한 Shared Lock을 사용하여 상태를 관리할 수 있습니다.
    
    장애에 대응하려면 vertx를 여러번 배포하면 됩니다. Vert.x를 클러스터 모드로 여러번 띄우면 훌륭한 클러스터가 생성됩니다. 이를 위해 유명한 IMDG인 Hazelcast의 도움을 받고 있습니다.
    

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
    
3. DeploymentOption
    
    각각의 verticle 에 대한 옵션을 정의할 수 있습니다.
    
    - setWorkerPoolSize() 워커 풀의 사이즈를 결정합니다. executeBlocking과 worker verticle 에 의해서 사용되는 풀입니다.
    - setWorker() 해당 Verticle 을 Worker 로 선언합니다.
    - setConfig() 설정 파일을 주입할 수 있습니다.
    - setInstances() 인스턴스 개수를 결정합니다. 인스턴스 개수는 eventLoopSize 보다는 많은 것이 좋습니다.
        
        이벤트 루프 사이즈는 Vertx 옵션으로 설정합니다.
        
    - setHA() ha 가용성 사용여부를 결정합니다.
4. Verticle
    
    Verticle은 Vert.x의 배치의 기본 단위로, Verticle은 Vert.x Instance 안에서 실행됩니다. Vert.x Instance 는 하나 또는 여러 개의 Verticle로 이뤄질 수 있습니다. 각각의 Verticle은 고유의 클래스 로더를 가질 수 있습니다. 이를 통해 Verticle간 스태틱 멤버과 글로벌 변수를 공유하지 않을 수 있습니다. Verticle 간의 통신은 Event Bus를 사용합니다.
    
5. ELP ( event loop thread)
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
6. Worker Verticle
    
    일반 Verticle은 ELP에서 순서대로 수행되므로, 긴 작업을 처리하기에는 효율적이지 못하다. Worker Verticle은 이를 극복하기 위해 만든 것으로 Thread Pool에서 비동기로 실행되는 Verticle이다. 
    
    일반 Verticle 은 하나의 이벤트루프(동일 쓰레드)에서만 작동합니다. 일반 verticle 은 처음 인스턴스가 초기화된 이후로 start 메서드가 호출된 이벤트쓰레드(e.g. A쓰레드)를 떠나지 않습니다. 해당 인스턴스의 모든 작업은 같은 쓰레드(e.g. A쓰레드)에서 실행됩니다. 따라서 상태값 등을 나타내는 변수 사용이나, 데드락 걱정 등에서 자유로워 질 수 있습니다. 대신, 오래 걸리는 작업을 실행 할 경우, 다른 이벤트의 처리가 막히게 됩니다. 오래 걸리는 작업은 다른 전체 작업 또한 느리게 할 수 있습니다. 일반 Verticle가 실행되는 event loop 쓰레드의 개수는 cpu core 수의 2배가 기본값으로, 이 보다 많은 verticle 이 작업 중이라면, 그 이상의 verticle 은 대기하게 됩니다. 
    
    Worker Verticle은 해당 작업만 worker pool로 실행되는 executeBlocking과 달리 작업이 끝나도 종료되지 않습니다. Worker Verticle 은 worker pool 의 쓰레드로 실행되므로, 긴 작업이 실행되더라도 앞단의 event loop 쓰레드를 점유하지 않습니다. Worker 전용 쓰레드는 기본적으로 20개가 존재합니다. 하나 이상의 쓰레드에서 동시 실행되지는 않습니다. 
    
    오래걸리는 작업의 예시는 다음과 같습니다.
    
    - JDBC 등의 무거운 Java 라이브러리를 사용 하는 경우
    - Bcrypt 등의 무거운(암호화 단계가 높은 경우) 연산이 들어가는 경우
    
    만약 오래걸리는 작업을 실행해야 하는 경우, 2가지 선택지가 있습니다.
    
    1. executeBlocking 메서드 사용하기
    2. worker verticle 로 작업하기 
7. 포트 공유
    
    같은 Vertx Instance 는 하나의 포트를 공유할 수 있다
    

## Vert.x 의 구성

Verticle Instance를 생성할 때마다 하나의 eventloop-thread가 할당됨

- EventLoop Thread
    
    Vertx 의 기본 쓰레드 구성
    
    main (자바 기본 쓰레드)
    
    vertx-eventloop-thread0, ...vertx-eventloop-threadn: 코어 수 * 2 만큼 자동 생성되는 쓰레드
    

## Vert.x와 Vert.x Instance

가장 작은 vert.x 서버를 만드는 방법

1. App 클래스
    
    ```java
    public class App extends AbstractVerticle{
        public static void main(String[] args) {
            Vertx v = Vertx.vertx();
            v.deployVerticle(new VertxServer());
        }
    
    		@Override
    		public void start() throws Exception {
    				vertx.createHttpServer()
    						.requestHandler(new Handler() {  
    				    public void handle(HttpServerRequest req) {
    				        String file = req.path.equals("/") ? "/index.html" : req.path;
    				        req.response.sendFile("web_root/" + file);
    				    }
    				}).listen(8080);
        }
        @Override
    		public void stop() throws Exception {
    			super.stop();
        }
    }
    ```
    

# Deployment

verticle을 전개하는 방법

vertx를 한번 실행하면 하나의 Vertx instance가 생성된다. 

하나의 Vertx instance는 하나의 eventbus와 다수의 verticles를 공유한다 

하나의 Vertx instance 는 port를 공유할 수 있다

이때, 같은 port를 사용하는 라우터가 2개 이상이라면 roundrobin 방식으로 포트가 공유된다

## 메모리 공유([https://hamait.tistory.com/428](https://hamait.tistory.com/428))

- Hazelcast사용 ([https://supawer0728.github.io/2018/03/11/hazelcast/](https://supawer0728.github.io/2018/03/11/hazelcast/))
    
    Hazelcast는 Map, Queue 등 여러 자료형을 지원하는 메모리 기반의 분산저장소로 빠른 속도를 자랑한다. Hazelcast는 객체 모든 노드가 같은 백업을 보관하며 Master / Slave가 따로 없으며, 한 member에서 실패가 발생할 경우, 다른 백업에서 복구된다. Hazelcast는 tcp기반의 p2p 통신을 사용한다
    
    Hazelcast는 Standalone모드와 Server/Client모드가 있다
    
    Standalone 모드는 각 애플리케이션에 데이터가 직접 올라가며, 데이터 접근 지연이 낮아 처리가 빠른 장점이 있다
    
    Server/Client 모드는 Hazelcast노드로 구성되며 애플리케이션과 별개로 확장이 가능한 장점이 있다
    
    Vert.x 에서는 VertxConfig를 구성할 때, ClusterManager로 Hazelcast Cluster Manager를 사용하면, HazelcastClusterManager의 map을 사용할 수 있다
    
    ```java
    Set<HazelcastInstance> instances = Hazelcast.getAllHazelcastInstances(); HazelcastInstance hz = instances.stream().findFirst().get(); Map map = hz.getMap("mapName"); // shared distributed map
    
    출처: https://hamait.tistory.com/428 [HAMA 블로그]
    ```
    
    1. ClusterManager 사용 비권장
    2. Hazelcast 사용 (블러킹될 수 있음 , worker node에서만 사용??)

```java
importio.vertx.core.AbstractVerticle;
importio.vertx.core.Verticle;
importio.vertx.core.http.HttpClient;
importio.vertx.core.http.HttpClientRequest;
importio.vertx.core.http.HttpClientResponse;
importio.vertx.core.http.HttpMethod;
importio.vertx.core.json.JsonObject;

public classVertxServer extendsAbstractVerticle {

    @Override
public voidstart()throwsException {
        System.out.println("start");
        vertx.createHttpServer().requestHandler(req -> {
            System.out.println(req.getParam("keyword"));
//            JsonObject jsonObject = req.body().result().toJsonObject();
//            String keywordName = jsonObject.getString("keyword_name");
//            String platform = jsonObject.getString("platform");
//            String headline = jsonObject.getString("headline");
//            String description = jsonObject.getString("description");
//            String finalUrl = jsonObject.getString("finalUrl");
//            String accountInfo = jsonObject.getString("accountInfo");
//            System.out.println(keywordName);
vertx.createHttpClient().request(HttpMethod.GET, 80, "https://210.220.163.82", "?q=hello")
                    .compose(HttpClientRequest::send)
                    .onComplete(resp->{})
                    .onSuccess(req2->{
                System.out.println(req2);
            }).onFailure(e->{
                System.out.println(e);
            });
        }).listen(10000);
    }

    @Override
public voidstop()throwsException {
super.stop();
    }
}

```

```
importio.vertx.core.Vertx;

publicclassApp {
publicstatic voidmain(String[] args) {
        Vertx v = Vertx.vertx();
        v.deployVerticle(newVertxServer());
    }
}

```