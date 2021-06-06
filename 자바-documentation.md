# 자바 주석 작성하는 방법

## Javadoc을 활용하자

javadoc은 javacode를 기반으로 html api 문서를 생성해주는 툴이다

### javadoc 적용대상

javadoc은 /** 주석을 대상으로 작동한다

### javadoc 문법

- @author

    작성자를 작성한다

- {@code }

    코드를 첨부한다

    ```java
    /**
     * <pre>
     * {@code
     * int a = 5;
     * }
     * </pre>
    ```

- @deprecated

    없어질 메서드, 클래스와 사유를 작성한다

    ```java
    /**
     * @deprecated 다른 메소드로 대체됨 {@link #helloWorld()}
     */
    void helloworld(){ ..
    ```

- {@link}, {@linkplain}

    참조링크를 생성한다 (예시는 deprecated 참조)

- @param

    매개 변수를 설명한다

    ```java
    /**
     * @param name 인사할 대상의 이름
     */
    void hi(String name){ ..
    ```

- @return

    반환값에 대해 설명한다

    ```java
    /**
     * @return 유저의 풀네임(성과 이름)
     */
    String getFullName(){ ...
    ```

- @see

    관련 항목의 링크를 생성한다

    e.g. 누르면 addFriend(String)으로 이동하는 링크

    ```java
    /**
     * @see #addFriend(String)
     */
    ```

    e.g. 누르면 documentation 사이트로 이동하는 링크

    ```java
    /**
     * @see <a href="doc.com/xxxxxx">documentation</a>
     */
    ```

- @since

    도입된 버전을 작성한다 

- @throws, @exception

    예외를 적는다, 같은 의미지만, 보통 throws를 더 많이 쓴다

    ```java
    @throws HelloWorldFailureException 헬로 월드가 실패할 때 발생한다
    ```

- {@value}

    static 변수의 상수값 설명

    ```java
    /**
     * MAX는 최대 인원의 수로 {@value}명 입니다.
     */
    public static final Integer MAX = 45;
    ```

    아래도 같은 뜻이지만, 이클립스의 경우 아래 문법은 허용하지 않는 것으로 알려져있다

    ```java
    /**
     * MAX는 최대 인원의 수로 {@value #MAX}명 입니다.
     */
    public static final Integer MAX = 45;
    ```

    출처: [https://stackoverflow.com/questions/47480314/javadoc-value-annotation-issue-in-eclipse](https://stackoverflow.com/questions/47480314/javadoc-value-annotation-issue-in-eclipse)

- @version

    현재 버전을 작성한다

## 자바 주석 작성의 원칙을 세우자

1. 모든 public 요소에 주석을 작성한다
2. 코드로 설명된다면 작성하지 않는다

## 자바 주석은 3가지가 있다

- //  줄 주석
- /*  블록 주석
- /** 자바 도큐먼트

## //  줄 주석

많은 개발자들에게 무시되는 주석이다. 낭비하지 말고, 바꾸면 안되는 코드라인 위에만 작성한다. 코드만 읽어도 이해가 된다면 주석을 쓸 필요없다.

- e.g. 바꾸면 안되는 코드를 설명한다

    ```java
    // 중요! create할때 create request에서 요구하는 항목
    this.useUpdate = false;
    ```

## /*  구현 주석

클래스나 메서드, case의 시작지점에 작성한다. 구현 내용을 설명한다

- e.g. 코드를 작성한 이유를 설명한다

    ```java
    void putSomeData(Some argument){
    	/* 
    	 * 이 컬력션의 요소가 할당받은 배열보다 많아지는 경우
       * 컬렉션의 공간을 2배를 늘리면 너무 커질 것 같아서 log(n) 만큼 확장합니다. 
    	 */
    }
    ```

    만약 주석이 없었다면, 갑자기 등장한 log 에 당황했을 것이다.

- e.g. 한계를 설명한다

    ```java
    String serialize(Object object, Class<T> tclass){
    	/*
    	 * cycle 이 존재하는 데이터는 아직 충분히 고려되지 않았습니다.
    	 * cycle 발견 시, 검출 지점을 null로 만듧니다.
    	 */
    }
    ```

    주석을 통해, 코드의 한계를 파악하고 개선방향을 정할 수 있을 것이다

## /** 문서 주석

이 주석은 자바독으로 변환될 수 있다.

코드 바로 위에  적는다. 실제 구현의 상세는 숨기고, 동작을 설명한다

- e.g. 동작을 설명한다

    ```java
    /**
     * 이 메서드는 현재 컬렉션의 첫번째 요소를 반환합니다. 
     * 만약, ~인 경우 ~를 반환합니다.
     *
     * @return 첫번째 요소
     */
    T first(){ ...
    ```

    실제 구현은 바뀔 수 있으므로 동작만 설명한다