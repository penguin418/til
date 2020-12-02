# SAP 다시 정리

[Repository and CTS](https://www.notion.so/Repository-and-CTS-69ebbfdfebd3406fb21e12b0db86b4f4)

[Selection Screen[⭐⭐]](https://www.notion.so/Selection-Screen-09b902bccdd444ba82db7c6a072aec79)

[Internal Table 사용하기](https://www.notion.so/Internal-Table-cec291b8e54242aca8791501a8a7fbc0)

[Authority Check](https://www.notion.so/Authority-Check-ec90bf1915f94e2e93bbaf653a887f5d)

[Selection Screen](https://www.notion.so/Selection-Screen-d5220f5adfb640a8b9188859f0db566e)

[Programs and Memory](https://www.notion.so/Programs-and-Memory-57943c50e82f4bf99146944cd7f02e13)

# ARCHITECTURE[⭐⭐⭐⭐⭐⭐⭐]

## Architectural Background⭐

계층 구조별 차이⭐🎓

Tier\Layer	프리젠테이션	애플리케이션	데이터
1계층 구조	클라이언트	클라이언트	클라이언트	수정이 힘듦
2계층 구조	클라이언트	클라이언트	데이터		DB 수정만 용이
3계층 구조	프런트엔드	미들 웨어	백엔드		모든 로직이 분리됨

용어

- 계층(Tier): 물리적 분리
- 층(Layer): 논리(기능)적 분리

## SAP System⭐⭐⭐

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

## **SAP Instance**⭐⭐⭐

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

# SAP GUI 사용법

## Basis

### 화면 구성

1. Command Field

    간단한 명령이 가능하다

    /n      	현재 트랜잭션을 종료한다
    /nxxxx	다른 트랜잭션으로 이동한다
    /o       	새로운 세션을 생성한다
    /oxxxx	새로운 세션에서 트랜잭션을 시작한다
    /i        	현재 세션을 닫는다
    /nex   	모든 세션을 닫는다

    T-CODE를 사용하여 트랜잭션으로 이동할 수 있다

    SE11	ABAP Dictionary                      	ABAP Dictionary확인
    SE18	BAdI Builder(Definition)          	BAdI 정의
    SE19	BAdI Builder(Implementation)	BAdI 구현
    SE24	Class Builder                            	글로벌 클래스와 인터페이스 관리
    SE37	Function Builder                      	함수 생성
    SE38	ABAP Editor                             	코드/텍스트엘리먼트/도큐먼트 확인
    SE80	Object Navigator                     	오브젝트 관리
    SE91	Message Maintenance            	메세지 클래스 관리
    SE93	Maintain Transaction               	T-CODE 부여하기, 트랜잭션 수정
    SM50	Work Processes of Instance    	로그온한 유저의 Work Process 확인
    SM51	Instances of SAP System         	Instance 확인

    1. Menu Bar (메뉴바)
    2. System Toolbar (시스템툴바)

        Save, Back, Cancel, Exit 등으로 구성된다

        Enhancement, Modify가 절대 불가능하다

    3. Application Toolbar
    4. Status Bar

## ABAP editor

### Pretty Printer (Shift + F1)

포맷을 예쁘게 맞추는 기능이지만, 미리 설정이 필요하다

1. [Utilities]>[Settings] 열기
2. ABAP Editor 탭 선택 > Pretty Painter 탭 선택
3. Indent 체크
4. Convert Uppercase / Lowercase 체크
5. Uppercase Keyword 체크

### 단축키

- 정의로 이동(더블 클릭)

    해당 데이터 오브젝트가 정의된 곳으로 이동한다

- 박스 선택 (Alt + 드래그)

    박스를 선택하는 기능이다

- 단어 선택 (Ctrl + 클릭)

    단어가 한번에 선택된다 (바로 복사되는 것은 아니다)

# DATA TYPES

ABAP에서 사용되는 변수를 정의하기 위해 사용되는 타입

Data type의 종류에는 Standard, Local, Global의 3가지 Type이 존재한다⭐

## Standard Types (Predefined Types)⭐

SAP에서 만든 기본 데이터 타입으로 길이 지정이 필요한지에 따라 Complete와 Incomplete타입이 있다⭐

### Complete Types

길이가 지정된 타입이다

D\(Date\) 	날짜 	8, 'YYYYMMDD' 형식
T\(Time\) 	시간 	6, 'HHMMSS' 형식
I\(Integer\)	정수 	4
F\(Float\) 	부동소수	8
STRING    	문자열	없음\(예외\)
XSTRING	HEX 스트링	없음\(예외\)

### Incomplete Types

길이가 지정되지 않은 데이터 타입으로 선언 시 항상 length를 지정해주어야 한다

C	문자열
N	숫자
X	HEX문자열
P	소숫점 있는 숫자

## Local Data Types

프로그램 내에서 정의한 데이터 타입으로 해당 타입을 정의한 프로그램내에서만 사용가능하다

## Global Data Types⭐⭐

ABAP Dictionary에서 정의한 타입을 말하며, 시스템 어디에서나 사용할 수 있고, 모든 사용자가 사용가능하다

- ABAP Dictionary에서 정의할 수 있는 Data Types는 3가지가 있다⭐
    1. Data Element: 필드 타입
    2. Structure: 구조체
    3. Table Type: 인터널 테이블
- Technical Attributes와 Semantic Attributes:⭐

    Data Types      	Technical ATTR	Semantic ATTR
    Local Data Types	Predefined       	없음
    Global Data Types	Domain           	Data Element, ..
    글로벌 데이터 타입은 Technical Attributes와 더불어 Semantic Attributes를 가질 수 있다.

    Technical Attributes는 D, T, I, F등으로 나타낼 수 있는 데이터 타입의 기술적 정보이다. 글로벌 타입의 Technical Attributes는 Predefined Type과 Domain을 통해 정의된다

    Semantic Attributes는 의미적 정보로, 주로 Data Element에 정의된다. Short Text, Field Label, Documentation, Search Help 등이 속한다

### 글로벌 데이터 타입 정의하기(e.g. Data Element)

Pre-defined타입을 사용하여 Data Element를, Pre-defined타입과 Data Element를 사용하여 Structure와 Table Type을 정의할 수 있다

1. ABAP Dictionary(SE11) 진입
2. [Data type] 선택>>새로운 데이터 타입 이름 입력>>[Create]
3. 팝업창>[Data Element]>[V]
4. Domain Type 혹은, Built-in Type(Pre-defined) 입력 후, 저장
5. 패키지 혹은 로컬오브젝트에 반영
6. 활성화

# ABAP SYNTAX[⭐⭐⭐⭐⭐⭐⭐⭐⭐]

## Data Object⭐⭐

ABAP 런타임에서 실제 메모리에 올라간 데이터이다

- Data Object의 상수 값은 3가지 종류가 있다:

    Literals, Constants, Text Symbols

### Literals

숫자 또는 문자열

```abap
"예제
2147483647 " 정수

'HELLO'    " 문자열 (String), 변수와 다르게 대소문자가 구별된다
'123.456'  " 문자열 (Decimal)
'20201011' " 문자열 (Date)

```

### Constants: 상수

변하지 않는 값, 로컬 상수

```abap
" 예제
CONSTANTS: co_max_line TYPE D VALUE '20201118'.   " VALUE 지정이 필수이다
CONSTANTS: co_nan(3) 'NAN'.     " 문자열, 숫자의 경우 타입 생략이 가능하다

```

### Text Symbols: 텍스트 심볼

다국어 지원 용도

```abap
" 1번 방법⭐
TEXT-nnn.      " Text Elment에 존재하는 텍스트를 참조함

" 2번 방법
'텍스트'(nnn). " Text Element에 추가
```

- Text Symbols 생성하기
    1. 소스코드 Editor 진입
    2. [메뉴바]>[Goto]>[Text Elements]>[Text Symbols] ⇒ (Text Elements 진입)
    3. SYM, TEXT에 각각 ID와 텍스트 입력
    4. 활성화
- Text Symbols에서 다국어 지원하기
    1. Text Elements 진입
    2. [메뉴바]>[Goto]>[Translation]⇒(Target Languange Selection 팝업 진입)
    3. 팝업-Target Language 입력 후, [V]
    4. 기존 Text Symbol 에 각각 매치되는 번역문 입력 후 저장
    5. 활성화
- System Fields: ABAP 런타임 환경에서 시스템 상태를 나타내는 읽기전용 변수이다. (SYST 테이블에 들어있다)⭐

    SY-UNAME	DB를 생성한 유저
    SY-LANGU	현재 유저의 언어
    SY-DATUM	현재 날짜
    SY-UZEIT 	현재 시간
    SY-SUBRC	직전 명령문의 성공여부, 성공 시 0
    SY-TCODE	실행중인 프로그램의 T-CODE
    SY-CPROG	실행중인 프로그램의 이름
    SY-REPID 	실행중인 프로그램????????????

## 선언문⭐⭐⭐⭐⭐

컴파일러에게 프로그램 내에서 사용하는 타입, 데이터, 테이블 등의 사용을 미리 알린다.

### 로컬 타입 선언하기

```abap
TYPES 로컬데이터타입 TYPE 타입.

" 한번에 여러 타입을 선언할 수도 있다
TYPES:
	로컬데이터타입 TYPE 타입,
	...
	로컬데이터타입 TYPE 타입.

" 복잡한 타입도 정의할 수 있다
TYPES: BEGIN OF 스트럭쳐이름, 
	필드이름 TYPE 타입,
	...
END OF 스트럭쳐이름.

```

```abap
" 예제
TYPES gv_1 TYPE D. " 콜론(:) 없이는 한번에 하나의 선언만할 수 있다
TYPES gv_2 TYPE T. " Complete 타입은 Length 지정이 필요없다

TYPES:
	gv_3 TYPE N LENGTH 4, " 한번에 여러 선언을 할때는 쉼표(,)로 구분해준다
	gv_4,                 " 지정 하지 않으면 길이 1의 C타입이 된다
	gv_5(4) TYPE C.       " 길이를 다른 형식으로 표현할 수 있다

```

### 변수 선언하기

```abap
DATA 변수이름 TYPE 타입.
DATA 변수이름(길이) TYPE  C | N | X | P. " C, N, X, P에 대해서는 길이를
DATA 변수이름 TYPE  C | N | X | P LENGTH 길이." 함께 선언할 수 있다
```

```abap
" 예제
DATA gv_a. " 기본형은 C이다

" 변수이름은 알파벳,숫자,_를 사용하고 30자까지 가능하며
" 알파벳, _으로 시작할수 있다
DATA: _12345678901234567890123456789 TYPE I, 

" VALUE는 값을 초기화하는 옵션이다
	gv_b TYPE F VALUE '1.2', 

" LIKE는 선언된 데이터의 타입을 사용하는 옵션이다
" 이때 gv_b의 값은 가져오지 않는다
  gv_c LIKE gv_b. 

" P 타입에만 존재하는 옵션 DECIMALS
" DECIMALS는 소숫점 이하 자릿수를 n로 하는 옵션이다
DATA: gv_d TYPE P DECIMALS 1.

" REF TO는 인스턴스를 선언하는 옵션이다 ⭐
DATA: gc_a TYPE REF TO cl_class1.  

" 스트럭트 타입으로 OCCURS n을 붙이면 헤더있는 테이블이 된다⭐
DATA: BEGIN OF gs_line1,
	f1 TYPE D, f2 TYPE T,
	f3 TYPE F, f4 TYPE I,
END OF gs_line1.

" 스트럭트 타입으로 OCCURS n을 붙이면테이블이 된다
DATA: gs_line2 TYPE sbook. 

" 테이블 타입 (WITH HEADER LINE 을 붙이면 헤더가 생긴다)
DATA: gt_itab1 TYPE sbook.
DATA: gt_itab2 TYPE TABLE OF sbook.

" 특수한 테이블 타입
" RANGE는 SIGN, OPTION, LOW, HIGH등을 추가로 사용하여
" 쿼리를 위한 조건을 생성한다
DATA: gt_itab3 TYPE RANGE OF sbook.

""""""""""""""""""""""""" 
" 헤더라인 있는 range에서만 사용가능
DATA: gt_itab4 TYPE RANGE OF sbook WITH HEADER LINE.
gt_itab4-SIGN = 'I'.    " I는 Include, E는 Exclude
gt_itab4-OPTION = 'EQ'. " EQ, BT, NE 등을 사용가능하다
gt_itab4-low = '150'.   " EQ에서는 같은 값을 찾는다
" gt_itab4-high         " BT 등에서 사용되는 옵션이다
APPEND gt_itab4.        " 적용한다 (Initialization에서 사용)
```

### 상수 선언하기

DATA와 동일하지만 VALUE 표시가 필수이다

```abap
CONSTANTS co_max_line VALUE 4. " VALUE가 필수임
```

### TABLES

Table과 동명의 Work Area를 선언한다.

프로그램 최상단(PROGRAM ~ 바로 아래)에 선언한다.

dynpro_fields(layout painter) 와 ABAP 사이에 데이터 교환을 해야하는 경우(예를들어 Module-Pool 프로그램에서 ABAP Dictionary 테이블 필드를 출력하는 경우), 코드에서 사용을 안하더라도 필수적으로 명시해야 한다⭐

```abap
TABLES: 테이블이름. " 테이블명과 동일이름의 struct 타입변수가 선언된다
```

### PARAMETERS

입력값을 받을 수 있다

SCREEN 1000에 출력되는 표준 selection screen⭐이 생성된다

```abap
PARAMETERS: 파라미터이름 TYPE 타입 [ DEFAULT 기본값 ]
" 추가적인 옵션은 selection screen부분을 참조한다
```

```abap
" 예제
" 체크박스
PARAMETERS: p1 AS CHECKBOX. 

" 라디오 버튼
PARAMETERS:
	p2 RADIOBUTTON GROUP r,    " 2이상의 멤버를 갖는
	p3 RADIOBUTTON GROUP r,    " 라디오 그룹이 필수이다
  pr4 RADIOBUTTON GROUP r, DEFAULT 'X'.

" 입력 필수
PARAMETERS:
	p5 OBLIGATORY
```

### SELECT-OPTIONS

SCREEN 1000에 출력되는 표준 selection screen⭐이 생성된다

```abap
SELECT-OPTIONS SO_필드 FOR 필드.
```

## 여러가지 함수⭐

여러가지 함수이다

### WRITE 문

화면에 변수의 값또는 글자를 출력한다

```abap
WRITE [ AT ] [/][위치][(최대출력칸수)] 문자열 혹은 변수.

```

```abap
" 예시
" 여러가지를 출력할 수 있다
WRITE: 'AB', '012'.

" AT는 써도 안써도 똑같다 ㅎㅎ
" /는 다음 줄에 출력하는 옵션이다
WRITE AT / 'ABC'.

"기준열
WRITE /'01234567890123456789'.

" 위치와 출력을 지정하는 옵션은 붙여써야 한다
" 변수를 사용해도 된다
WRITE /10(5) 'ABCDEFG'.

"""""""""""""""""""""""""""""""""""""""""""
" 결과는 다음과 같다
AB012
ABC
01234567890123456789
          ABCDE

```

### MESSAGE문

message를 출력한다

message문을 사용하기 위해서는 message클래스 등록이 필요하다

- 메시지 클래스 생성방법
(실무에서는 개인적으로 만들지 않고 BC에서 생성한 것을 사용한다)
    1. Message Maintenance(SE91)에 진입
    2. 새로운 메시지 클래스 입력후, [Create]
    3. Short Text 입력 후, [Save]
    4. Request 등록 후 Message 탭으로 이동
    5. 메시지 등록

    ```abap
    " 방법 1 👓
    REPORT 레포트이름 MESSAGE-ID 메시지클래스이름.
    ...
    MESSAGE 타입메시지아이디.

    " 방법 2
    MESSAGE 타입메시지아이디(메시지클래스).

    ```

    ```abap
    " 예시
    " 메시지 클래스가 다음과 같다고 가정하자
    Message Class ZMSG

    No. Message Short Text
    000 &
    001 &는 옳바른 입력값이 아닙니다
    """""""""""""""""""""""""""""""
    REPORT ... MESSAGE-ID 메시지클래스.
    MESSAGE I(000) WTIH '문자열'. " &에 값을 넣어준다
    MESSAGE W(001) WITH '문자열'. 

    " 타입과 의미를 숙지할 것⭐
    " E ERROR				에러 출력
    " W WARNING			경고
    " I INFORMATION	정보
    " A TERMINATION	종료
    " S SET TEXT		성공
    " X SHORT DMUMP	덤프
    ```

## 제어문 - 조건문⭐

조건을 사용하여 실행의 위치를 제어하는 명령문이다

### IF문

조건문이다

```abap
... SQL 문
IF gv_data <> INITIAL.
ENDIF.

" INITIAL과 DEFAULT⭐
" INITIAL은 해당 타입의 기본 값이다
"  * C타입은 ' ', I타입은 0
" DEFAULT는 프로그램에서 변수를 정의할 때 미리 지정한 값이다
```

### WHEN문

2가지 사용법이 있다

```abap
CASE SY-SUBRC.         	| CASE 'x'.
	WHEN 'EXIT'.         	|   WHEN pa_opt1.
		LEAVE TO SCREEN 0. 	|   WHEN pa_opt2.
ENDCASE.               	| ENDCASE.
```

## 제어문 - 반복문

조건이나 횟수동안 실행의 위치를 제어하는 명령문이다

### DO문

루프를 수행한다

```abap
DO.           	| DO 10 TIMES. " n번 수행한다
	...         	|   ... 
	IF 종료조건.	|   " SY-INDEX는 1부터 증가한다
		EXIT.     	| ENDDO.
	ENDIF.
ENDDO.

```

### WHILE문

조건이 만족하는 동안 루프를 수행한다

```abap
WHILE 조건. " ABAP에는 boolean 타입이 없으므로
	...       " 무한루프를 위해서는 
ENDWHILE.   " WHILE 1 = 1. 을 사용하면 된다

```

### LOOP AT문

테이블의 각 ROW를 순회한다

```abap
LOOP 
	AT 인터널테이블이름 
	INTO 워크에이리어이름. " WHERE문으로 조건을 줄 수 있다
	... " SY-index는 1부터 증가
ENDLOOP.

```

### 제어문 - Subroutine

### Perform문

같은 로직을 수행하는 코드를 모아놓고 이동해서 실행하는 명령문이다

# MODULARIZATION[⭐⭐⭐⭐⭐⭐⭐]

재사용 규칙에 대해 알아본다

로컬에는 Subroutine과 Local Method가, Global에는 Function Module과 BAPI가 있다

## Local Modularization⭐⭐⭐⭐

### Subroutine(PERFORM ~ FORM)

프로그램에서 정의할 수 있는 모듈화 방법이다

```
PERFORM 서브루틴이름 USING 변수A.    " actual parameter
PERFORM 서브루틴이름 CHANGING 변수A. " 같은 의미이다
" 필요없는 경우 USING ~, CHANGING ~ 은 생략할 수 있다
" Call By Ref, Value, Ref and Result에 상관없이 
" PERFORM에서는 actual parameter만 적는다

PERFORM 서브루틴이름 TABLES 테이블. 
" 테이블을 사용할 경우 TABLES를 사용한다

"""""""""""""""""""""""""""""""""""""""""""
FORM 서브루틴 USING 변수p_A. " p_A를 formal parameter라고 부른다
	... " Call By Reference이다⭐ 
	... " formal에 대한 수정이 actual에도 적용된다
ENDFORM.
"""""""""""""""""""""""""""""""""""""""""""
FORM 서브루틴 USING VALUE(변수p_A). 
	... " Call By Value이다⭐
	... " 복사해서 가져오므로 큰 값은 안가져오는 것이 좋다
	... " formal(p_A)의 수정내용은 FORM 내에서만 유효하다
	... " actual(A)를 직접수정하면 적용된다
ENDFORM.
" actual parameter는 기존값이 그대로다
""""""""""""""""""""""""""""""""""""""""""""
FORM 서브루틴 CHANGING 변수. "Call By Reference이다⭐
	... (생략)
FORM 서브루틴 CHANGING VALUE(변수) " Call by value and return이다⭐
	... " ~ return는 정상종료 시에만 값을 반영한다 
	... " 비정상종료: exit, TYPE E message

```

### Methods in Local Class

로컬 클래스의 메소드를 의미한다

## Global Modularization⭐⭐⭐

### Function Group

Function Module을 갖는 프로그램이다

프로그램명은 다음과 같다

- SAPL<Function Group이름>⭐

Program처럼 여러 element를 갖는다⭐

- Screen, Status, Include, T-Code 등
- Function group 생성하기
    1. Object Navigator(SE80)에 진입한다
    2. Function Group에서 새로운 Function Group이름을 입력하고 [Enter]
    3. 이관한다

### Function Module

중앙집중식으로 관리 가능한 서브루틴

- Subroutine과 다른점👓⭐

    Function group이라는 Pool 에 속한다

    입출력 데이터를 인터페이스로 갖는다

    자체적인 Exception을 가지고 있다

    활성화해야 사용할 수 있다

    독립적으로 테스트 가능하다

- Function Module 생성하기👓
    1. 펑션 빌더(SE37)에 진입한다
    2. 새로운 Function Module이름을 입력하고 [Create]
    3. [팝업창]-Function group과 short text를 입력하고 [V]
    4. 필요한 탭을 입력한다
    Import: 입력값을 받을 변수를 정의한다

        Export: 출력값을 보낼 변수를 정의한다

        Changing: 입력변수에 다시 출력한다

        Tables: 사용하는 인터널테이블을 정의한다

        Exceptions: 예외를 정의한다

        Source code: 소스코드

    5. 저장 및 활성화
- Function Module을 사용하는 방법👓

    ```
    CALL FUNCTION '함수이름'
    	EXPORTING 함수의 변수 = 값 혹은 변수
    	IMPORTING 함수의 변수 = 프로그램내 변수
    	EXCEPTIONS 예외이름 = 1.
    IF SY-SUBRC <> 0. " 예외처리
    ENDIF.
    ```

### Methods in Global Class

글로벌 클래스의 메소드

### BDC(옛날기능, 시험에도 안나온다)

사용자의 행동 목록을 명령어로 구현한 매크로기능이다

- BDC 구현방법
    1. Transaction Record(SHDB)에 진입한다
    2. [New Recording]
    3. [팝업창]-Recording에 새로운 레코드이름, Transaction code에 BDC를 시작할 트랜잭션 입력 후 [Start recording]
    4. 작업 수행 후, [Exit]으로 종료
- 조회모드⭐

    A: 화면을 보면서 수행, E: 에러 발생 시만, N: 화면 표시 없음

### BAPI

BDC의 개선버전. BDC와 다르게 Standard Table에 대해서만 수행할 수 있음

- BAPI 사용하는 방법

    BAPI Explorer(BAPI)를 사용해 검색한다

    이름(BAPI_로 시작한다)을 찾으면 일반함수처럼 call function으로 사용한다

- BAPI 특징

    에러메시지를 export해서 돌려준다

# 디버거

## 이동방식

- 싱글스텝(라인바이라인)
- 컨티뉴

## 검사방식

- 브레이크포인트

    원하는 라인에서 멈출 수 있다

    /h 옵션으로 시작하자마자 멈출 수 있다

- 와치포인트⭐

    변수를 설정하여 변수를 추적할 수 있다

    조건을 주어, 변수가 해당하는 값이 될때로 이동할 수 있다

# COMPLEX DATA OBJECT[⭐⭐⭐]

Complex Data Object에는 2종류가 있다

- Structured Data
- Internal Table

## Structured Data⭐

- 생성하는 방법

    ```abap
    " 스트럭트 타입으로 OCCURS n을 붙이면 헤더있는 테이블이 된다⭐
    DATA: BEGIN OF gs_line1,
    	f1 TYPE D, f2 TYPE T,
    	f3 TYPE F, f4 TYPE I,
    END OF gs_line1.

    " 스트럭트 타입으로 OCCURS n을 붙이면테이블이 된다
    DATA: gs_line2 TYPE sbook. 
    ```

## Internal Table⭐⭐⭐

- 생성하는 방법

    ```abap
    " 헤더 있는 테이블
    DATA: BEGIN OF ... OCCURS n.
    ...
    DATA: END OF ...

    " 테이블 타입 (WITH HEADER LINE 을 붙이면 헤더가 생긴다)
    DATA: gt_itab1 TYPE bc_400_s_flight.
    DATA: gt_itab2 TYPE TABLE OF sbook.

    " 특수한 테이블 타입
    " RANGE는 SIGN, OPTION, LOW, HIGH등을 추가로 사용하여
    " 쿼리를 위한 조건을 생성한다
    DATA: gt_itab3 TYPE RANGE OF sbook.

    """"""""""""""""""""""""" 
    " 헤더라인 있는 range에서만 사용가능
    DATA: gt_itab4 TYPE RANGE OF sbook WITH HEADER LINE.
    gt_itab4-SIGN = 'I'.    " I는 Include, E는 Exclude
    gt_itab4-OPTION = 'EQ'. " EQ, BT, NE 등을 사용가능하다
    gt_itab4-low = '150'.   " EQ에서는 같은 값을 찾는다
    " gt_itab4-high         " BT 등에서 사용되는 옵션이다
    APPEND gt_itab4.        " 적용한다???? 안되넹
    ```

- Internal Table의 Attributes 3가지: ⭐

    Standard: 디폴트이다, 인덱스(row num)를 통해 접근하기 좋다, 가장많이 쓴다, 가장 느리다, 트리구조, 바이너리 서치 옵션이 가능하다(사전 정렬 필수)

    Sorted: Key에 대해 정렬된 데이터 상태, key를 통해 접근한다(Unique혹은 Non-Unique Key 사용 필수). Sort사용할 수 없다

    Hashed: Key로만 access할 수 있다, Index없다, 가장 빠르다

- Internal Table 가능 연산 (ACIDR)

    APPEND, INSERT, READ, CHANGE, DELETE

- work area 사용하기

    ```abap
    data gt_테이블이름 type table of 테이블이름.

    " 선언 방법
    data gs_테이블이름 type 테이블이름.
    data gs_테이블이름 like line of gt_테이블이름.

    insert gs~ into table gt~ index 2.
    ```

- begin~occurs, with header line

    헤더가 생긴다

    디버거에서 모자를 확인할 수 있다

- refresh,  free, clear 데이터 날림⭐

    refresh gt_테이블이름	바디 다 날림
    free gt_테이블이름  	바디 날리고 메모리 반납
    clear gt_테이블이름	헤더만 날림, 헤더없으면 다 날림
    clear gt_테이블[]     	바디만 날림

# TRANSPARENT TABLE[⭐]

## Transparent Table

- ABAP Dictionary(SE11)에 만드는 테이블
- 생성 시 DB에 똑같은 모양의 테이블 생성됨
- 프로그램에서 스트럭쳐 타입으로 생성됨⭐

# OPEN SQL ↔ NATIVE SQL

# 클래스(OOP)

## OOP의 속성(ABAP)

- Encapsulation: 필요한 변수와 함수를 한데 묶는다
- Polymorphism: 하나의 이름으로 다양하게 정의해
- Inheritance: 상속(Inheriting from)
- Event Control: 이벤트를 전달한다

## Global Class와 Local Class

### 로컬 클래스

로컬 클래스는 프로그램 내에 정의 / 구현된 클래스이다

- 로컬 클래스 정의와 구현

    ```abap
    CLASS <클래스이름> DEFINITION.
    	PUBLIC SECTION.          " IMPLEMENTATION과 다르게 섹션 정의가 필수이다
    		METHODS: <메소드이름>. " 메소드 정의
    	PRIVATE SECTION.
    		...
    ENDCLASS.

    CLASS <클래스이름> IMPLEMENTATION.
    	METHOD 메소드이름.
    	ENDMETHOD.
    ENDCLASS.
    ```

    이 중에서 실제 메소드를 선언하는 방법은 다음과 같다

    ```abap
    " 정의의 경우
    ... SECTION.
    	METHOD <메소드_이름>
    		IMPORTING               " 함수의 입력이다 
    			파라미터_이름 TYPE ... " 변수명은 iv_~ 의 형태를 많이 쓴다
    		CHANGING                " 함수가 수정하는 테이블이다
    			파라미터_이름 TYPE ... " 변수명은 cv_~ 의 형태를 많이 쓴다
    		EXPORTING               " 함수의 출력이다
    			파라미터_이름 TYPE ... " 변수명은 ev_~ 의 형태를 많이 쓴다
    		EXCEPTION 
    			에러_타입.             " 변수명은 ~_type 의 형태를 많이 쓴다
    		
    		
    ```

- 로컬 클래스 사용

    ```abap
    DATA: 인스턴스이름 TYPE REF TO 클래스.⭐
    CREATE OBJECT 인스턴스이름.

    CALL METHOD 인스턴스이름->인스턴스메소드이름. " 매개변수가 없는 경우

    CALL METHOD 인스턴스이름->인스턴스메소드이름  " 매개변수가 있을 경우
    	EXPORTING 매개변수1이름 = 값 
    	           ...
    						 매개변수n이름 = 값.

    인스턴스이름->인스턴스메소드이름( ). " CALL METHOD를 생략할 경우 ( )를 써주어
                                       " 일반 ATTRIBUTES와 구별해주어야 한다
    ```

- Functional Method ⭐

    RETURNING을 사용하는 메소드이다

    export, changing는 사용불가하다

    다른 언어의 함수처럼 리턴값을 사용할 수 있다

    - move, case, loop, if, while, compute 등에서 사용된다
- Constructor⭐

    클래스에 단 하나 존재한다

    메소드 이름은 constructor여야 한다

    import, exception만 사용된다

    public section에만 정의될 수 있다

    create object호출 시 implicit하게 수행된다

- Static_Constructor⭐

    클래스에 최대 하나 존재한다

    메소드 이름은 static_constructor여야 한다

    파라미터를 사용할 수 없다

    처음 호출 되었을때 implicit하게 수행된다 (후보군)

    - 생성자
    - static method호출시
    - static attribute 사용시
    - event handler 사용시
- Self Reference⭐
    - me->
    - 클래스 내 메소드에서 로컬변수와 클래스 내 변수를 구분하기 위해 사용된다. 이런 특수한 경우를 제외하면 생략가능하다

        ```abap
        CLASS 클래스 DEFINITION.
        	...
        	DATA var. " 로컬변수와 이름이 곂친다
        ENDCLASS.

        CLASS 클래스 IMPLEMENTATION.
        	METHOD 메소드.
        		DATA var.      " 로컬변수
        		var = me->var. " attr를 할당한다 
        	ENDMETHOD.
        ENDCLASS.
        ```

### 글로벌 클래스

글로벌 클래스는 시스템에 정의 / 구현된 클래스이다

1. 글로벌 클래스 생성

    SE80 (Object Navigator)에서 생성한다

    Class Builder 사용

    [생성 옵션](https://www.notion.so/d214a41fd82e4fcda63efa35a8000a37)

    Attributes 탭에 변수 입력

    Methods 탭에 메소드 입력, Parameter를 클릭하여 파라미터 정의

## 인터페이스

1. 클래스와의 차이

    정의만 존재한다

    ```abap
    INTERFACE 인터페이스이름.
    	METHOD 메소드이름.
    	DATA   변수이름 TYPE 타입. " ABAP에는 변수가 정의에 존재한다
    ENDINTERFACE.
    ```

    인터페이스 명시는 PUBLIC섹션에만 가능하다

    ```abap
    CLASS 클래스이름 DEFINITION.
    	PUBLIC SECTION.
    		INTERFACES: 
    			인터페이스이름.
    "   METHODS:                     " 인터페이스 메소드는 정의하지 않는다
    " 	  인터페이스이름~메소드이름.
    ENDCLASS.
    ```

    인터페이스를 구현할 때는 ~(물결)이 사용된다

    ```abap
    CLASS 클래스이름 IMPLEMENTATION.
    	METHOD 인터페이스이름~메소드이름.
    	ENDMETHOD.
    ENDCLASS.
    ...
    " 사용할 때도 물결이 필요하다
    클래스인스턴스->인터페이스이름~메소드이름( ).
    ```

## 캐스팅

1. 정의: 강제형변환
2. 업캐스팅(Narrow Cast)
업캐스팅은 인터페이스 혹은 부모 클래스로 형변환 하는 캐스팅으로, 인터페이스, 혹은 부모 클래스의 함수에만 접근할 수 있다
    - 배경 상황

        ```abap
        DATA: gt_interfaces TYPE TABLE OF REF TO zinterface,
        			go_interface  TYPE REF TO zinterface,
        			go_class      TYPE REF TO zclass.
        CREATE OBJECT go_class.
        APPEND go_class TO gt_interfaces.
        CREATE OBJECT go_class.
        APPEND go_class TO gt_interfaces. " 업캐스팅 발생
        CREATE OBJECT go_class.
        ```

    ```abap
    APPEND go_class TO go_interfaces.    " 업캐스팅 발생

    LOOP AT gt_interfaces INTO go_interface.
    	go_interface->interface_method( ). " 작동함

    	" go_interface->method( ).         " 오류 발생
    ENDLOOP.
    ```

3. 다운캐스팅
다운캐스팅은 자식클래스로 형변환 하는 캐스팅으로, ?= 연산자를 사용한다

    ```abap
    TRY.                                  " 인터페이스를 여러 클래스가 사용하므로
    	go_class ?= go_interface.           " TRY블록과 ?= 연산자를 사용한다
    CATCH CX_SY_MOVE_CAST_ERROR.
    	...
    ENDTRY. 
    ```
