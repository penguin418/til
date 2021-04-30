자바8의 변경사항

1. 람다 표현식

    ```java
    new Thread(new Runnable() {
       @Override
       public void run() { 
          System.out.println("무명 클래스"); 
       }
    }).start();
    ```

    ```java
    // 컴파일러가 인수타입을 추론할 수 있으므로 인수가 있더라도 타입 생략가능
    new Thread(()->{
          System.out.println("람다식");
    }).start();
    ```

    @FunctionalInterface

    람다는 지연연산을 추구하지만, 저장해두었다 사용할 수 도 있다

    ```java
    @FuncionalInterface
    Interface Converter{
    	public String convert(String str);
    }
    ....
    	Converter addMp3Extension = (x) => x + ".mp3";
    	String filename = addMp3Extension(hash);
    ```

1. 스트림 API

    컬렉션과의 차이

    - 저장공간이 없음 (연산 파이프라인에서만 존재)
    - 함수적 특성을 가짐 (소스에는 변화없음)
    - 지연연산 추구 (람다를 사용하여 지연연산을 추구)
    - 단계별 문제해결 (필터링, 정렬, 중복제거, 계산, 매핑 등)

    지원 메소드

    - distinct : 중복제거
    - sorted 정렬
    - filter( 조건 ) : 조건이 true인 것 필터링
    - count() : 개수 세기, 단 한번만 사용가능
2. Optional 클래스

    Optional은 값이 없을 수 있는 객체임을 분명하게 나타낼 수 있다

    ```java
    // null
    Optional<Member> thisIsNull = Optional.empty();

    // null이면 에러발생
    Optional<Member> shouldbBeMember = Optional.of(repository.getMember(1));

    // null일 수도 있지
    Optional<Member> nullOrMember = Optional.ofNullable(repository.getMember(1));
    ```

    Optional을 사용하면 값 하나를 위해 많은 try catch를 사용하지 않아도 된다

    ```java
    Optional<String> name = nullOrMember.flatMap(Member::name);

    String name = nullOrMember.map(Member::name)
    													.orElse("no name");
    ```

    여러가지 편의기능

    - 기본값: orElse()
    - optional이 없을때만 기본값 생성: orElseGet()
    - 예외처리: orElseThrow()
    - 존재할때만 다음내용 실행 ifPresent(x → {})

    길이가 0~1인 컬렉션과 동일하게 stream기능도 지원한다

    - filter
    - map

자바7의 변경사항

1. 생성 타입 생략 (다이아아몬드 오퍼레이터)

    ```java
    Map<String, Integer> scoreMap = new HashMap<>();
    ```

2. 스트링 스위치

    스트링 사용 가능~

    ```java
    switch(error.getMessage()){
    case "password": ...
    case "username": ...
    }
    ```

3. 오토 클로저블 (try with resource)

    알아서 close해주는 auto closable (또는 auto disposable)

    ```java
    try(
    	FileChannel inChannel = (new FileInputStream(SRC_FILE_NAME)).getChannel();
    	FileChannel outChannel = (new FileOutputStream(DEST_FILE_NAME)).getChannel();
    ){
    	outChannel.transferFrom(inChannel, 0, Long.MAX_VALUE);
    }catch( ... )
    ```

4. 멀티 예외 타입

    A | B | C 로 받을 수 있다. 서로 상속 관계에 속하면 안된다

    ```java
    try{
    	...
    }catch(UTFDataFormatException | InterruptedIOException e){
    }
    ```

5. NIO.file 패키지

    Paths 지원

    ```java
    Path path = Paths.get("c:\Temp\temp");
    path.getNameCount(); // 2, 경로 내 노드 개수
    path.getFileName(); // temp.txt
    path.getRoot(); // c:
    path.getParent()); // c:\Temp 
    ```

    파일 감시

    ```java
    WatchService  watchService = FileSystems.getDefault().newWatchService();
    path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY,ENTRY_DELETE);
    while(True){
    	WatchKey key = watchService.take();
    	for (WatchEvent<?> e: key.pollEvents()){
    		// kind 와 context 를 통해 파일과 이벤트 종류를 알 수 있다
    	}
    }
    ```

6. 포크조인

    RecursiveAction(반환x), RecursiveTask(반환o) 클래스를 생성하여, 분할 정복 작업을 수행할 수 있음

    Sub Task로 분리 후, 분할정복 수행

    ```java
    ForkJoinPool pool = new ForkJoinPool(Runtime.getRunTime().availableProcessors());
    pool.invoke(task);
    ```
출처: https://www.oreilly.com/content/java7-features/