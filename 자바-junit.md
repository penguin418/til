### 자바 유닛테스트

# 작성 요령

given-when-then 방식

1. given: 어떤 상황에서
2. when: ~을 하면
3. then: ~게 된다

arrange-act-assert 방식

1. arrange: expect, test 데이터 준비
2. act: 동작 테스트
3. assert: expected, assert 비교

Hamcrest 활용

```java
// 기존에 쓰던 코드
Assertions.assertEqual( expected, calculated );

// 가독성 좋은 코드 (matcher 를 사용)
assertThat( calculated, is(expected) );
```

참고: [http://iyoon.github.io/jekyll/update/2015/03/02/jUnit.html](http://iyoon.github.io/jekyll/update/2015/03/02/jUnit.html)

[https://www.slideshare.net/wakaleo/junit-kung-fu-getting-more-out-of-your-unit-tests](https://www.slideshare.net/wakaleo/junit-kung-fu-getting-more-out-of-your-unit-tests)

# 테스트 방법

### assertEquals(a, b)

두 값이 일치

### assertArrayEquals(a, b)

두 배열의 값이 일치

### assertThrows(Class<T> expectedThrowable, ThrowingRunnable runnable)

예외 발생

```java
RuntimeException exception = assertThrows(RuntimeException.class, ()->{
	// ...
});
```

### assertDoesNotThrow(Executable executable) (junit5부터)

예외 발생 안함

```java
assertDoesNotThrow(()->{
	// ...
});
```

### assertTimeout(Duration timeout, Executable executable)

시간 초과됨

```java
assertTimeout(Duration.ofMillis(500), ()->{
	// ...
});
```

### assertAll

모든 조건을 만족

```java
assertAll(
		() -> {assertTrue(...)},
		// ...
		() -> { };
);
```

# 편의 기능

### @DisplayName

test에 표시되는 이름을 지정할 수 있다

test이름을 한글로 적어도 되니 그냥 한글메서드 쓰자

# 테스트 순서

### @Before (Junit5에서 @BeforeEach)

각 @Test 전에 매번 호출됨

### @After (Junit5에서 @AfterEach)

각 @Test 이후 매번 호출됨

### @BeforeClass (Junit5에서 @BeforeAll)

모든 @Test 전에 호출되며, 초기화에 사용된다

테스트의 독립성이 회손될 수 있음을 유의하자

```java
@BeforeClass
public static void beforeTest(){
	...
}
```

- 정적 메소드
- 하나만 선언할 수 있음

### @AfterClass (Junit5에서 @AfterAll)

모든 @Test 이후 호출됨

### @TestMethodOrder (Junit5부터)

메소드 실행순서 지정 가능

- OrderAnnotation 방식

    Order 숫자가 작은 것부터 시작한다

    test3 → test1

    ```java
    @TestMethodOrder(OrderAnnotation.class)
    public class ExampleTest{
    	@Test
    	@Order(2)
    	public void test1() {
    		.. 
    	}

    	@Test
    	@Order(1)
    	public void test3() {
    		.. 
    	}
    }
    ```

- Alphanumeric 방식

    알파벳 숫자 순서로 시작한다

    test1 → test3

    ```java
    @TestMethodOrder(OrderAnnotation.class)
    public class ExampleTest{
    	@Test
    	public void test1() {
    		.. 
    	}

    	@Test
    	public void test3() {
    		.. 
    	}
    }
    ```

- custom 방식도 있다.

    안쓸 것 같다

### @FixMethodOrder(MethodSorters.NAME_ASCENDING) (Junit4 지원)

알파벳 숫서로 실행한다

참고: [https://www.baeldung.com/junit-5-test-order](https://www.baeldung.com/junit-5-test-order)

# Junit Rule

### ExternalResource 룰

### ErrorCollector

문제가 생기더라도 계속 실행하는 룰

```java
public class ExampleTest{
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	// ...
	@Test
	public CreateTest(){
		collector.addError(new Throwable(...));
		// ...
		collector.checkThat(getResult(), not(containsString("ERROR"));
		// 여기서 모든 에러를 체크한다
	}
}
```

제공하는 메서드

- addError(Throwable error)

    직접 에러를 추가할 수 있다

    추가한 에러는 checkThat에서 검증에 실패했을 때 출력된다

    ```java

    try{
    	//... 테스트 중
    }catch(SomeException e){
    	collectors.addError(e);
    }
    ```

- checkSucceeds(Callable<T> callable)
- checkThat(String reason, T value, Matcher<T> matcher)
- checkThat(T value, Matcher<T> matcher)

    ```java
    estimation = 10;
    result = ...
    collector.checkThat(estimation, equalTo(result));
    collector.checkThat(something, is(true));
    ```

- verify()

### ExpectedException 룰

예외 예측 룰

```java
public class ExampleTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void someTest(){
		exception.expect(RuntimeException.class);
		exception.expectMessage("오 이거 망한듯"); // 추가적인 기능
		// ...
		throw new RuntimeException("오 이거 망한듯");
	}
	
```

### Timeout 룰

시간 룰

```java
public class ExampleTest {
	@Rule
	public Timeout timeout = Timeout
			.builder()
			.withTimeout(1, TimeUnit.SECONDS)
			.build();
	
	@Test
	public void test1() {
		// ... TimeOutException 발생 가능
	}
```

참고: [https://github.com/junit-team/junit4/wiki/rules](https://github.com/junit-team/junit4/wiki/rules)

[https://www.codota.com/code/java/methods/org.junit.rules.ErrorCollector/checkThat](https://www.codota.com/code/java/methods/org.junit.rules.ErrorCollector/checkThat)

[https://www.swtestacademy.com/junit-rules/](https://www.swtestacademy.com/junit-rules/)