#  NoSQL

(NotOnlySQL)이라는 뜻

데이터간 관계로 설계 X

- `RDBMS는 어떻게 응답해야 하는가?`로 시작

접근 알고리즘으로 설계 O

- `NOSQL은 어떻게 질문해야 하는가?`로 시작

##  목적

기존 시스템과의 비교

- 기업의 데이터를 저장하고 관계를 정의, 분석하는데 최적화
- 기업에 특화된 데이터, 복잡하지만 양은 적음

NoSQL 등장 배경

- SNS로 인한 대규모 데이터가 생산됨

- 양은 많지만, 단순한 형태를 지님

- 구글과 아마존에 의한 Bigtable과 Dynamo 논문 발표
- Not Only SQL 탄생

NoSQL 특징

- RDBMS와 다른 형태의 데이터 저장구조 총칭
- *데이터 간의 관계(e.g. Foreign Key)를 정의하지 않음
- 페타바이트 급의 대용량 데이터 처리가능
- 데이터를 분산해서 저장하고 상호 복제하여 장애가 발생해도 데이터 유실이나, 서비스 중지없이 운영가능
- 테이블의 스키마가 유동적
- ID 키에만 타입이 동일함.

CAP 이론

- 분산 컴퓨팅 환경은 특징이 3개 있고, 2개만 만족 가능함
- 일관성(Consistency)
- 가용성(Availability)
- 부분결함 용인(Partitionaing)

###  종류

- Key / Value 조합

- Unique Key에 하나의 Value를 가진 형태

- put, get 형태의 API 주로 이용됨

- Oracle Coherence, Redis 등

- 키에 대한 제약 -> Ordered Key로 발전함

- Ordered Key / Value Store 조합:

- 데이터는 내부적으로 Key 순서로 sorting되어 저장돔

- Key 내부에 colume: value 조합의 여러 필드를 가짐

- Hbase, Cassandra 등

- Document Key / Value Store 조합

- Key/Value Store의 확장형

- 저장되는 Value의 타입이 json, xml, yaml 등의 document

- MongoDB, CouchDB 등

- Full Text Search Engines

- Apache Lucene, Apache Solr 등

- Graph 데이터베이스

- neo4j, FlockDB 등

##  장단점

모델링 기법 비교

- 관계형 모델링 기법

- 저장할 도메인 모델 분석

- 개체 간의 관계 식별

- 테이블 추출 <- 어플리케이션에 맞는 쿼리 개발

- 테이블을 이용한 쿼리 구현

- NoSQL의 데이터 모델링

- 도메인 모델 분석

- 쿼리 결과 도출

- 테이블 설계

- NoSQL 모델링 특징

- 관계형 모델보다 더 깊은 데이터 구조 및 접근 알고리즘에 대한 이해 필요

- NoSQL 쿼리가 실제 몇 개의 물리 노드에 걸쳐 수행되는지 이해해야 제대로 쿼리 디자인이 가능

- NoSQL 디자인은 DB, 어플리케이션 외에도, 네트워크나 디스크 등 인프라에 대한 디자인도 함께 해야함

- 대부분의 NoSQL DB는 인증이나 인가체계가 없음, 보안에 취약, 별도의 보안 체계 필요(reverse proxy등)

RDBMS <->NoSQL

- 범용, 고성능, 안정적<->특정용도 특화, 솔루션 특징 이해 필요

- 데이터의 일관성 보증

- 정규화를 전제로 하며 업데이트 비용이 낮음<->분산이 전제

- 불필요한 중복 없음

- 복잡한 쿼리(Join등) 가능<->Join불가

- 대량의 데이터 입력 처리<->배열 형태의 데이터 고속 처리

- 테이블 인덱스 생성, 스키마 변경 힘듦

- 개발, 운영시에 칼럼 확정이 어려운 경우<->모든 데이터 저장

#  NoSQL 데이터 모델링

비정규화(중복허용), Aggregate(1:n→ nested로 구성), Application Side Join

##  NoSQL 데이터 모델링

###  비정규화(Denormalization): 데이터의 중복 허용

- 쿼리 프로세싱의 최적화 또는 특정 데이터 모델에 맞추기 위해 같은 데이터를 여러 도큐먼트나 테이블에 중복

NoSQL은 질의 결과를 단순화하기 위해 사이즈를 희생하는 구조임

- 쿼리당 I/O, 쿼리데이터 사이즈 vs 전체데이터 사이즈

- 쿼리 수행을 위한 모든 데이터를 모아서 수행하므로 I/O 숫자가 줄어듦

- 반면 사이즈는 필연적으로 증가

- 프로세싱 복잡도 vs 전체 데이터 사이즈

- 쿼리 친화적으로 데이터를 모을 수 있지만 전체 사이즈가 커짐

###  Aggregate

‘유연한 스키마’ 속성은 복잡하고 다양한 구조의 내부 요소(nested entities)를 가진 데이터 클래스를 구성 가능

- 1:n 관계를 최소화하여 Join연산을 줄임

- 복잡하고 다양한 비즈니스 요소를 담고 수정 가능

예를 들어 단어장과 단어를 조인해서 가져오지 않고, 하나의 단어장에 단어를 모두 넣어 단일 집합 모델(Aggregate: Wordbook)을 구성함

- 단어장의 모든 속성을 수용가능

- 단어장은 간단한 예로, 실제로는 더 다양한 단일집합이 가능,

- 경우에 따라서는 단일집합모델 간 차이도 클 수 있다

- 예를 들면 어떤 단어장에는 녹음과 사진까지 들어있고 어떤 단어장에는 위키가 들어있을 수도 있는 것

###  Application Side Join

조인은 어플리케이션에서 수행

- NoSQL은 대용량 데이터에 대해 빠른 응답성능과 확장성, 가용성을 최우선 목적으로 함
- 따라서 쿼리 타임에 Join을 피하도록 모델을 구성함
- 단, 데이터가 수시로 변경되는 경우, 비정규화와 aggregation을 통한 중복되는 데이터에 대해 해당 데이터를 모두 업데이트하여 많은 비용이 발생하므로 변경이 잦은 데이터를 테이블로 독립시켜 쿼리타임 조인 수행

###  원자적 집계

어떤 NoSQL은 트랜잭션을 제공하지만 안 제공하는 경우도 있다

이때, 원자적 집계 패턴을 구현하여 트랜잭션을 대체할 수 있다

어떤 시나리오에서는 NoSQL이 RDBMS보다 트랜잭션을 잘 지원할 수도 있다

예를 들면, 단어장을 지우는 일

- RDBMS라면 단어장을 지우고 단어를 모두 지워야 한다
- NoSQL에서는 단어장을 지우면 모두 끝난다

##  NoSQL  인덱싱

###  Composite Key Index

- 하나 이상의 필드를 deliminator를 이용해 구분지어 사용하는 방법

- Key: windows:etc value: . . .

- key: windows:program_files value: . . .

- Ordered KV Store는 이를 이용해 order by같은 sorting, grouping 구현
- N개의 서버로 구성된 클래스로 동작하고 데이터는 Key를 기준으로 N개의 서버에 나눠서 저장됨
- Key 선정 시, 전체 서버에 걸쳐 부하가 골고루 분산될 key를 골라야 함

###  Invert Search Index

- Value의 내용을 key로, key의 내용은 반대로 value에 저장
- 검색엔진에서 많이 사용하는 방법
- 검색엔진은 검색 로봇이 모든 페이지를 검색 후, 문서 내의 단어들을 색인하여 URL에 맵핑하여 저장해 놓음

###  계층 데이터구조 모델링 패턴

- NoSQL에서 RDBMS의 기법을 모방하여 Tree 같은 계층형 구조를 저장하는 기법을 총칭

###  Tree Aggregation

- Tree 구조 자체를 하나의 value에 저장
- Json이나, xml등을 이용, 트리구조를 정의, value에 저장
- tree가 크지않고 변경이 많이 없는 경우 적합

- key: . . . value: a: { b: { c: { .. . .

###  Materialized Path

- Tree구조를 테이블에 저장할 때, 현재 노드까지의 전체 경로를 Key로 저장
- 구현에 드는 노력은 크지만 효율적인 저장방식임
- Key 검색 시 정규식 사용이 가능
- Key: A
- key: A/B/C

##  NoSQL 데이터 모델링

###  도메인 모델 파악

- ERD 그리기

###  쿼리 결과 디자인

- 어플리케이션에서 쿼리가 수행되는 결과값을 먼저 정함
- 출력 형식을 기반으로 필요한 쿼리/테이블 정의

###  패턴을 이용한 데이터 모델링

- Get, Put만 이용해서 처리할 수 있도록 재정의

###  기능 최적화

- 첨부파일: 포스팅이 의존적이며 수량과 변경이 적으므로 하나의 필드에 모아서 저장
- 분류에 따른 포스팅: 포스팅에 분류필드를 별도로 넣음

- value 내의 필드를 secondary index로 활용

- 필드에 따라 where문으로 접근

###  NoSQL선정 및 테스트

- 데이터 모델에 가장 어울리는 NoSQL
- 선정된 NoSQL 최적화

- 하드웨어 디자인

[https://nol2soft.wordpress.com/2015/10/14/nosql-데이터-모델링-기법들/](https://nol2soft.wordpress.com/2015/10/14/nosql-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%AA%A8%EB%8D%B8%EB%A7%81-%EA%B8%B0%EB%B2%95%EB%93%A4/)