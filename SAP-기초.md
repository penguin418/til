# Architecture⭐⭐⭐⭐⭐⭐⭐

# ARCHITECTURE BACKGROUND

계층 구조별 차이⭐🎓

Tier\Layer	프리젠테이션	애플리케이션	데이터
1계층 구조	클라이언트	클라이언트	클라이언트	수정이 힘듦
2계층 구조	클라이언트	클라이언트	데이터		DB 수정만 용이
3계층 구조	프런트엔드	미들 웨어	백엔드		모든 로직이 분리됨

용어

- 계층(Tier): 물리적 분리
- 층(Layer): 논리(기능)적 분리

# SAP SYSTEM

SAP System는 3 계층으로 이루어져있다

- Presentation, Application, Database

### Presentation Level

SAP 화면을 구성한다

SAP GUI에는 for Windows, for Java, for HTML 3가지가 존재한다⭐

### Application Level

SAP Instance는 SAP 시스템 컴포넌트들의 관리 단위(Unit)로 단일 컴퓨터 당 하나 혹은 여러 개가 존재할 수 있다. 다음 3가지가 존재한다⭐

1. PAS(Primary Application Server)

    가장 먼저 설치되어야 하는 인스턴스로 이외에는 모두 AAS(Additional AS)이다

2. CSI(Central Service Instance)

    Message Server와 Enqueue Work Process를 제공한다⭐

    - Message Server는 인스턴스 간의 통신을 제공한다
    - Enqueue Work Process는 시스템의 Logical Lock을 제공한다
3. SAP Instance ( =AS(Application Server))

### Database Level

보통 SAP는 독립적인 DBMS위에 구성된다

DB Interface를 가지고 있어 어떤 DB라도 사용할 수 있다 (이식성이 높다)

database에는 Cross-Client데이터, Client-Specific 데이터를가 존재한다

1. Cross-Client

    Client에 상관없이 볼 수 있는 데이터로, Repository가 해당된다

    - Repository

        모든 시스템 개발 도구로 구성된다

        Program, function module, database table definition등이 속한다

2. Client-Specific

특정 Client로 로그인한 경우에만 조회할 수 있는 데이터이다

- Application Data
- Customizing Data

# SAP INSTANCE

각 SAP Instance는 Dispatcher, Work Process, Local Buffer로 구성된다⭐

1. Dispatcher⭐

    Presentation Level을 통합 관리한다

    Work Process 요청을 관리(FIFO)하고 분배한다

    Shared Memory로 작업정보를 관리한다

2. Work Process 종류⭐
    1. Dialog:

        화면을 관리한다. 시스템 당 2개 이상 있다

        모든 유저가 공유한다

    2. Update: Update를 관리한다. 시스템 당 하나 있다
    3. Background: 스케줄을 관리한다. Dispatcher당 하나있다
    4. Lock Management: 락을 관리한다. 시스템 당 하나있다
    5. Spool: 출력 프로세스를 관리한다 별로 안중요하다
3. Local Buffer

    Open SQL을 사용할 경우, Local Buffer를 사용한다
