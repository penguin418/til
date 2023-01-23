# AMQP 표준

메시지 브로커에 대한 종속성을 낮추기 위한 개방형 표준 응용 계층 프로토콜

- 목표: 보안, 신뢰성, 상호 운용성, 표준, 개방성
- RabbitMQ: AMQP 0.9.1 (2008년) 스펙을 최초로 구현한 브로커
- 네트워크 레벨 스펙인 AMQP와 어플리케이션 레벨 스펙인 AMQ 모델로 정의.

# AMQP 스펙

- 네트워크 레벨 스펙
- 통신 과정을 정의
- 물리 연결인 Connection 과 논리 연결인 Channel 을 통해 연결 시간을 줄여 빠른 통신이 가능

## 컴포넌트

1. connection
    - TCP기반의 실제/물리 연결
    - 멀티플렉싱 지원(하나의 Connection으로 여러 Channel의 입출력을 지원)
2. channel
    - 각 쓰레드에서 사용하는 경량/논리 연결 (Connection Context 공유)
    - Channel 과 Queue는 1:1 관계
    - 클라이언트가 연결을 성공하면 채널을 열고 유지
    - 더 이상 필요하지 않으면 채널을 닫고 리소스를 회수

# AMQ 모델 스펙

어플리케이션 레벨 스펙으로 브로커 구현시 지켜야 하는 규격

## Queue(메시지 대기열)

- 소비자가 가져가기 전까지 메시지를 저장하는 버퍼 역할의 컴포넌트
- 이름: amq로 시작하는 이름은 사용 불가
- durable: 활성화 시 브로커가 재시작 하는 경우에도 큐가 보존되는 옵션
    - durable 큐는 디스크에 저장되며, durable 하지 않은 큐는 메모리에 저장되며 transient 큐라고 불림.
    - 브로커가 재시작되는 경우 큐는 복원되나, 메시지는 persistent 가 활성화된 메시지만 보관됨
- exclusive: 활성화 시 큐는 단 하나의 커넥션에 의해서만 사용되며, 해당 커넥션이 닫히면 큐가 삭제되는 옵션
- auto-delete: 활성화 시 마지막 Consumer가 떠나면 큐가 삭제되는 옵션

## Exchange

- 메시지 브로커에서 Queue에 메시지를 전달하는 역할의 컴포넌트
- name: 이름
- durable: 활성화 시 브로커 재시작에도 exchange를 보존하는 옵션
- auto-delete: 활성화 시 마지막 큐가 제거될 때 exchange를 삭제하는 옵션
- 약자로 쓰일 때 `X` 로 표현됨

### Exchange Type

- 직접교환(DirectExchange)
    - 메시지를 RoutingKey와 일치하는 Queue로 전달
    - 1:1 관계(producer-queue), 한번만 처리가 필요할 때
- 팬아웃(FanoutExchange)
    - `amq.fanout`
    - 메시지를 바인딩된 모든 Queue에 전달
    - 브로드캐스팅이 필요할 때
        - 온라인 게임의 순위표를 업데이트하는 경우
        - 스포츠 뉴스 사이트에서 점수를 실시간으로 클라이언트에 배포하는 경우
    - 높은 처리량이 필요할 때
- TopicExchange: 메시지를 RoutingKey와 일치하는 하나이상의 Queue로 전달
    - `amq.topic`
    - 주제별로 구독이 필요할 때
        - 지리적 위치(GEO)와 관련된 데이터 배포
        - 태그로 분류된 시스템이 있을 때, 각 태그에 추가할 뉴스를 업데이트
        - 서비스 오케스트레이션
- HeaderExchange: 메시지에 설정한 헤더 기반으로 전달
    - `amq.match`
    - where-match 큐 별로 배정 옵션 사용. 어떤 헤더 키(where)의 값이 조건에 맞을 때(match)
    - 기본적으로는 TopicExchange 와 같은 사용사례이나, any, all 옵션으로 여러 기준을 추가가능

## Binding

- Exchange와 Queue의 관계를 정의한 라우팅 테이블

## Consumer

- Queue에 있는 메시지를 소비
- 단 하나의 Queue에 연결
- Push API 또는 Pull API 사용
    - Push API
        - Queue에서 Consumer에 메시지를 보냄
    - Pull API
        - `basic.get` 사용
        - Consumer가 Queue에서 메시지를 가져옴
- consumer tag
    - queue 가 consumer를 구분하기 위해 사용하는 태그
    - 구독 api의 성공 응답에 포함되며 구독 취소 api에 사용됨.
- Prefeching
    - Consumer가 미리 메모리에 저장해둘 수 있는 최대 메시지 량
    - Push API 모드일때, Queue는 Consumer의 Prefetch 만큼 한번에 보내줄 수 있어 효율적.
    - 각 메시지의 처리가 빠를 수록 prefetch를 높여 네트워크 지연을 없애는 것이 유리
- ack
    - `basic.deliver`, `basic.get-ok` , `basic.ack` 등
    - Queue가 Consumer 의 메시지 수신 여부를 확인하는 방법
    - Consumer가 ack 응답없이 종료될 경우 Queue는 다른 Consumer 에게 메시지를 전달하거나 다른 Consumer가 Queue에 등록될때까지 대기함
- reject
    - Consumer가 메시지를 처리하는데 실패하는 경우, Queue에게 처리 실패를 알리는 방법
    - `basic.reject`