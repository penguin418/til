# RabbitMQ 연습

## spring-rabbitmq-monolith
스프링 rabitmq 예제 실습

## spring-rabbitmq-monolith
스프링 rabitmq 활용 이메일 서비스 구성
* 이메일 처럼 sendmail 또는 Postfix 같은 외부 서비스와 통신하는 서비스는 지연이 발생가능하므로, 느슨한 연결로 구성해야 함.

# RabbitMQ 

## 용어
* Producer(Publisher): 메시지 발신자
* Exchange: 메시지를 큐에 분배
* Queue: 메시지 저장 큐
* Binding: Exchange와 Queue의 mapping
* Consumer(Subscriber): 메시지 수신자
* RoutingKey: 라우팅 키

## exchange
* 직접교환(DirectExchange)
  * 메시지를 RoutingKey와 일치하는 Queue로 전달
  * 1:1 관계(producer-queue), 한번만 처리가 필요할 때
* 팬아웃(FanoutExchange)
  * 메시지를 바인딩된 모든 Queue에 전달
  * 브로드캐스팅이 필요할 때
    * 온라인 게임의 순위표를 업데이트하는 경우
    * 스포츠 뉴스 사이트에서 점수를 실시간으로 클라이언트에 배포하는 경우
  * 높은 처리량이 필요할 때
* TopicExchange: 메시지를 RoutingKey와 일치하는 하나이상의 Queue로 전달
  * 주제별로 구독이 필요할 때
    * 지리적 위치(GEO)와 관련된 데이터 배포
    * 태그로 분류된 시스템이 있을 때, 각 태그에 추가할 뉴스를 업데이트
    * 서비스 오케스트레이션
* HeaderExchange: 메시지에 설정한 헤더 기반으로 전달
  * where-match 큐 별로 배정 옵션 사용.
    어떤 헤더 키(where)의 값이 조건에 맞을 때(match)
  * 기본적으로는 TopicExchange 와 같은 사용사례이나, any, all 옵션으로 여러 기준을 추가가능

## queue(큐, 대기열)
아래 속성은 생성자에서 추가 가능
* 이름: 큐의 이름
  * `amq` 로 시작하는 이름은 사용불가
* 지속성(durable): 브로커 재시작시 큐(대기열) 유지 여부
  * 사용시 메시지가 디스크에 저장됨
  * 미사용시 메모리에만 저장(transient)
* 배타성(exclusive): connection 종료 시 큐 삭제 여부
* 자동삭제(auto delete): 모든 소비자가 subscribe를 종료하면 큐 삭제

## 메시지
* ttl: `expiration` 헤더에 지정가능