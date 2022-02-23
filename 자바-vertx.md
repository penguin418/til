# vert.x

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
    
    -