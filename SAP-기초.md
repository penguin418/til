## Architecture

- 계층과 층

    계층(Tier): 물리적 분리

    층(Layer): 논리적 분리

    [계층별 차이⭐🎓](https://www.notion.so/3dd57c8f87484af086f0471a014c6b4f)

- SAP는 3계층 구조를 사용한다. (현행 SAP ECC의 전버전인 SAP R/3)⭐
    1. Presentation Layer

        SAP GUI (for Windows, for JAVA, for HTML)

    2. Application Layer

        ### Instance: Dispatcher + Work Process + Local Buffer

        Dispatcher⭐⭐⭐⭐⭐

        Work Process를 분배하는 역할을 한다

        Shared Memory로 작업정보를 관리한다

        Work Process 종류⭐⭐⭐⭐⭐

        1. Dialog:

            화면을 관리한다. 시스템 당 2개 이상 있다

            모든 유저가 공유한다 

        2. Update: Update를 관리한다. 시스템 당 하나 있다
        3. Background: 스케줄을 관리한다. Dispatcher당 하나있다
        4. Lock Management: 락을 관리한다. 시스템 당 하나있다
        5. Spool: 출력 프로세스를 관리한다 별로 안중요하다

        Local Buffer

        Open SQL을 사용할 경우, Local Buffer를 사용한다

        다른 Instances

        - Central Instance는 다음을 갖는 Instance이다

            Message Server: User를 가장 부하가 적은 서버에 연결해준다

            Enqueue Work Processor: 락을 관리한다

        - Dialog Instance는 작업을 하는 Instance이다

            load balancing을 위해 사용된다

    3. Database Layer
        - Database Instance

        SAP는 독립적인 DBMS위에 구성된다 (이식성이 높다)

        곧 SAP HANA DB만을 사용하겠다고 했다

## SAP GUI

1. Command Field

    명령이 가능하다

    [가능한 명령들](https://www.notion.so/f2e57e849d5b42c5b6d366d46eaaabca)

    T-CODE를 사용하여 트랜잭션으로 이동할 수 있다

    [T-CODE](https://www.notion.so/cc174f85a5fb4def88d7a4faa5b7075f)

2. Menu Bar (메뉴바)
3. System Toolbar (시스템툴바)

    Enhancement, Modify가 절대 불가능하다

4. Application Toolbar
5. Status Bar

## CTS

Repository(=Dictionary): Client에 관계없이 볼 수 있는 Program, Data, 등등

ㄴApplication Component: FI, MM, CO 등등

ㄴPackage: 관련 오브젝트

ㄴObject: 하나의 오브젝트는 하나의 Package에만 할당 가능

Dev(개발) 서버 → QAS(테스트) 서버 → PRD(운영) 서버

- Change Request

    수정한 내역을 다른 시스템에 반영(transport)하는 작업

- 개발 기본

    개발자가 개발한 프로그램의 이름은 Z, Y로 시작해야 한다

    프로그램은 Save상태와 Activated버전이 존재하고, Active되야 실행가능하다
