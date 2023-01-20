# RabbitMQ 연습

## spring-rabbitmq-monolith
스프링 rabitmq 예제 실습

# RabbitMQ 

## 용어
* Producer(Publisher): 메시지 발신자
* Exchange: 메시지를 큐에 분배
  * DirectExchange: 메시지를 RoutingKey와 일치하는 Queue로 전달
    * 1:1 관계, 한번만 처리가 필요할 때
  * FanoutExchange: 메시지를 바인딩된 모든 Queue에 전달
    * 1:n 관계, 높은 처리량이 필요할 때
  * HeaderExchange: 메시지에 설정한 헤더 기반으로 전달
    * where-match 큐 별로 배정 옵션 사용.
      어떤 헤더 키(where)의 값이 조건에 맞을 때(match)
  * TopicExchange: 메시지를 RoutingKey와 일치하는 하나이상의 Queue로 전달
    * 주제별로 구독이 필요할 때
* Queue: 메시지 저장 큐
* Binding: Exchange와 Queue의 mapping
* Consumer(Subscriber): 메시지 수신자
* RoutingKey: 라우팅 키