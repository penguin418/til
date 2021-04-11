# 몽고db

### MongoDB설치

mongodb는 메모리 관리를 운영체제에 맡김, 메모리 관리를 잘하는 unix가 유리

### Mongodb실행

1. 설치 확인

    데이터 저장용 폴더 생성

    ```sql
    mkdir data
    ```

    몽고 db실행

    ```sql
    mongod -dbpath <데이터 저장용 폴더 경로>
    ```

    몽고 db 클라이언트실행

    ```sql
    mongo
    ```

    클라이언트 명령어

    show dbs: 용량 확인

    show collections: 컬렉션 확인

2. 서비스 실행

    설정 파일 생성

    ```sql
    # mongodb.conf
    dbpath = /..../var/data
    logpath = /.../var/log/logs.log
    logappend = true
    rest = true
    directoryperdb = true
    ```

    서비스 등록

    ```sql
    mongod --config <설정파일 경로> --install
    ```

    이후 실행하지 않아도 자동 실행됨

3. 서비스 종료

    ```sql
    몽고db 클라이언트 쉘> use admin;
    몽고db 클라이언트 쉘> db.shutdownServer();
    ```

# MongoDB Shell

데이터베이스 조회

- 각 컬렉션들의 용량 확인

    ```sql
    > show dbs
    ```

- 컬렉션 조회

    ```sql
    > show collections
    ```

    * 컬렉션에는 스키마가 존재하지 않는다

기본적인 자바스크립트 명령

- 할당(var를 쓰지 않아도 된다)

    ```sql
    > a = 5;
    5
    ```

- 사칙연산

    ```sql
    > a + 10;
    15
    ```

- 반복문

    ```sql
    > for(i=0;i<3;i++) { print("hello"); };
    hello
    hello
    hello
    ```

- json 데이터 저장

    ```sql
    > var a = { age: 15 };
    {
      "age": 15
    }
    ```

데이터 추가

- save 명령

    ```sql
    > db.scores.save( {a:99} )
    "ok"
    ```

    만약 db에 scores라는 컬렉션이 존재하지 않는 경우, 생성해 준다

- find 명령

    ```sql
    > db.scores.find();
    [
    	{ "a": 99, "_id": { "$oid": "312314213..." } }
    ]
    ```

- 자바스크립트 문법을 함께 사용할 수 도 있다

    ```sql
    > for(i=0;i<3;i++) { db.scores.save( { b: i } );
    "ok"
    > db.scores.find();
    [
    	{ "a": 99, "_id": { "$oid": "312314213..." } }
    	{ "b": 1, "_id": { "$oid": "537314213..." } }
    	{ "b": 0, "_id": { "$oid": "964735213..." } }
    	{ "b": 2, "_id": { "$oid": "274383613..." } }
    ]
    ```

데이터 조회

- find명령

    아무 인자 없이 사용하는 find명령은 모든 속성을 가져온다

    인자를 주어 조건에 맞는 값을 찾을 수 있다

    and연산

- find 명령 (비교 연산자)

    ```sql
    > db.socres.find( {a: { $gt: 15 } } );
    [
    	****{ "a": 99, "_id": { "$oid": "312314213..." } }
    ]
    > db.socres.find( {b:2 } );
    [
    	{ "b": 2, "_id": { "$oid": "274383613..." } }
    ]
    ```

    gt: >, lt: <, gte: ≥, lte: ≤, ne: ≠, 

    in: [ ], nin: [ ]

- find명령 (논리연산자)

    ```sql
    > db.socres.find( {a: { $gt: 15, $lt: 99 } } );
    []
    ```

    ```sql
    > db.socres.find( $or: [{'a':{"$gt":15}},{'a':{"$lt":99}}] );
    []
    ```

- find명령 (field 존재여부)

    ```sql
    > db.scores.find( {a: ($exists: true} } );
    [
    	{ "a": 99, "_id": { "$oid": "312314213..." } }
    ]
    ```

- use <db이름>

## MongoDB 특징

- c++로 개발됨
- 스키마를 고정하지 않음
    - 데이터를 구조화해서 json으로 저장
- join이 불가능하므로 join없이 데이터 설계 필요
- 메모리맵 형태의 파일엔진 DB
    - 메모리 크기가 성능 제어
    - 메모리를 넘어서는 경우, 성능 급격하락
- 삭제가 없는 경우가 적합
    - 로그 데이터
    - 이벤트 참여 내역
    - 세션
- 트랜잭션(금융, 결제, 빌링, 회원정보)에는 부적합
    - 보안과 일관성이 중요한 정보는 RDBMS에 저장할 것
- 도큐먼트 데이터 모델
    - 속성의 이름, 값으로 이뤄진 쌍의 집합
    - 속성은 문자열, 숫자, 날짜, 배열, 다른 도큐먼트 지정 가능
    - 하나의 document에 필요한 정보를 모두 담아야함
    - one query로 모두 해결이 되게끔 collection model 설계 필요
    - join 안되므로 미리 embedding할것

### JSON

javascript object notation

- javascript의 array문법으로 데이터 구조를 기술
    - XML의 유연성, XML보다 적은 오버헤드
    - 클라이언트에서 처리 퍼포먼스 증가
    - 송수신에 더 유리
- light weight data형식
- 사람이 읽고 쓰기 쉽고, 기계가 파싱하고 생성하기 쉬움

### JSON 표기법

- { } 중괄호로 시작하여 끝남
- 문자열과 값으로 구분
- 각 멤버는 ,로 구분
- object, string, number, array, true, false, null, 배열

## 장/단점

### 일반적인 장단점

Schema less구조

- 다양한 형태의 데이터 저장 가능
- 데이터 모델의 유연한 변화

Read Write성능 뛰어남

Scale Out구조

- 많은 데이터 저장가능
- 장비확장 용이

Json구조: 데이터 직관적

↔ 데이터 업데이트 시, 데이터 손실 가능(부분 결합)

↔ 많은 인덱스사용시 충분한 메모리 확보 필요

↔ 데이터 공간 소모가 RDBMS에 비해 많음(비효율적 Key중복 입력)

↔ 복잡한 Join사용시 성능제약

↔ transaction 지원 약함

↔ 기본으로 제공하는 MapReduce 작업이 Hadoop에 비해 떨어짐

### 빅데이터 처리 특화

Memory Mapped

- 데이터 쓰기 시 가상 메모리에 저장
- 비동기로 디스크에 저장
- 방대한 데이터 빠르게 쓰기 가능
- 메모리 다 차면 하드디스크에 저장 ↔ 속도 느려짐, 하드웨어 투자 필요

### 불안정성

- 데이터 많은 경우 일부 데이터 손실 가능성 존재
- 샤딩의 비정상적 동작 가능성
- 레플리카 프로세스의 비정상 동작가능성(데이터 복제 원할하지 못함)

### 변론

- RDBMS보다 훨씬 빠름
- 싱글노으, 멀티노드간 성능차 거의 없음
- MongoDB Multi Node 사용시 Insert연산실패 발생 가능
- RDBMS보다 낮은 비용으로 빠른 성능 제공 가능
- 쓰기 100배이상 빠름, 읽기 3배이상 빠름, 읽기쓰기 동시 3배이상 빠름