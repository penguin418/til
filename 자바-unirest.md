## Unirest-Java

rest api 호출을 지원하는 가벼운 라이브러리

### 시작하기

- gradle 의존성 추가

    ```java
    implementation 'com.konghq:unirest-java:3.11.09:standalone'
    ```

- 응답 결과를 객체로 받는 경우 mappers의존성 추가

    ```java
    implementation 'com.konghq:unirest-object-mappers-gson:3.11.09'
    ```

### 구조

Unirest는 내부적으로 UnirestInstance 클래스 타입의 단일 객체를 사용하는 프록시 스태틱 클래스로, 실제 요청은 BaseRequest 클래스가 처리한다

- Unirest 내부에 UnirestInstance를 갖는다

    ```java
    public class Unirest {
    	private static UnirestInstance primaryInstance = new UnirestInstance(new Config());
    	...
    ```

- UnirestInstance 내부에 Config를 갖는다

    ```java
    public class UnirestInstance implements AutoCloseable {
    	private final Config config;
    ```

- 실제 Method는 BaseRequest에 구현되있다

    ```java
    	...
    	publicGetRequestget(String url) {
    		return new HttpRequestNoBody(config, HttpMethod.GET, url);
    	}
    ```

- 실제 요청이 발생하는 시점은 리턴 타입을 호출했을 때이다.

    ```java
    HttpResponse response = Unirest.delete("http://httpbin.org/delete").asEmpty()
    ```

### 메소드

get, post, put, delete, patch, head, option을 모두 지원한다

- get() : GetRequest

    get은 자원을 조회하는 데 사용된다

    ```java
    final HttpResponse<String> response = Unirest.get(BASE_URL).asString();
    System.out.println(response);
    ```

- head() : GetRequest

    헤더만 요청할 때 사용된다

- options() : GetRequest

    서버의 옵션을 확인하는데 사용된다

- post() : HttpRequestWithBody

    post는 자원을 등록하는 데 사용된다

    ```java
    HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/api")
          .header("accept", "application/json")
          .queryString("apiKey", "123")
          .field("parameter", "value")
          .field("firstname", "Gary")
          .asJson();
    ```

    - 비동기
- put() : HttpRequestWithBody

    put은 자원을 수정하는 데 사용된다

- put() : HttpRequestWithBody

    patch도 자원을 수정하는 데 사용된다

- delete() : HttpRequestWithBody

    delete는 자원을 삭제하는 데 사용된다

    ```java
    Unirest.delete("http://httpbin.org/delete").asEmpty()
    ```

### 메소드 옵션

http메서드 실행 시, 실제로는 GetRequest와 HttpRequestWithBody가 실행된다

GetRequest는 부모클래스인 HttpRequest의 옵션만을 제공한다

- routeParam(String name, String value)

    routeParam(Map<String, Object> parameters)

    패스 파라미터를 입력한다

    ```java
    Unirest.post("http://httpbin.org/delay/{time}")
    		.routeParam("time", "1")
    		.asEmpty();
    ```

- queryString(String value, Object value)

    queryString(Map<String, Object> parameters)

    쿼리 스트링을 사용할 수 있다

    ```java
    Unirest.get("http://localhost:8080/api")
    		.queryString("fruit", "apple")
    		.asString();
    ```

    queryString이 2개 이상인 경우 다음과 같이 사용한다

    ```java
    Unirest.get("http://localhost:8080/api")
    		.queryString("fruit", Arrays.asList("apple", "orange"))
    		.queryString(ImmutableMap.of("droid", "R2D2", "beatle", "Ringo"))
    		.asString();
    ```

- basicAuth(String username, String password)
- accept(String value)

    accept 헤더를 작성해준다

    .e.g application/json 입력 시, Accept: application/json

- responseEncoding

    .e.g. gzip 입력 시, Accept-Encoding: gzip

- header(String name, String value)
- headerReplace(String name, String value)
- cookie(String name, String value)

    cookie(Collection<Cookie> cookies)

    쿠키를 추가한다, 덮어 쓰는 것이 아님

- socketTimeout(int millies);

결과를 반환하는 방법은 아래서 다룬다

HttpRequestWithBody의 옵션

GetRequest는 부모클래스인 HttpRequest은 물론 확장된 옵션들을 제공한다

- field(String name, Object value)

    field(String name, Collection<?> value)

    field(String name, Object value, String contentType)

    fields(Map<String, Object> parameters)

    field(String name, File file)

    form 의 필드를 입력할 수 있다

    파일도 입력가능하다

    ```java
    InputStream file = new FileInputStream(new File("/MyFile.zip"));

    Unirest.post("http://localhost:8080/api")
    		.field("upload", file, "MyFile.zip")
    		.asEmpty();
    ```

- body(String body)

    body를 입력할 수 있다 

    post의 경우 기본으로 Content-Type=text/plain이 적용된다

    ```java
    Unirest.post("http://localhost:8080/api")
    		.body("This is the entire body")
    		.asEmpty();
    ```

- 하지만, 객체를 통해 입력하려면 application/json으로 바꿔주어야 한다

    ```java
    Unirest.post("http://localhost:8080/api")
    		.header("Content-Type", "application/json")
    		.body(new SomeUserObject("Bob"))
    		.asEmpty();
    ```

- charset(Charset charset)

    ASCII, UTF-8, UTF-16, UTF-16LE, UTF-16-BE 등을 지원한다

### 결과 처리

결과의 종류에는 empty, byte, file, json, string, object 등이 있다

각 종류마다 as[Type] 메서드를 사용해 받을 수 있고 리턴타입은 HttpResponse<Type>이다

- asEmpty()

    요청 후, 응답을 파싱하지 않는다

    async메소드엔 asEmptyAsync(Callback<Empty> callback), asEmptyAsync()가 있다. 결과가 없어, asEmptyAsync()도 있다 

- asString()

    응답을 문자열로 받는다

- asObject(클래스.class)

    응답을 객체로 받는다. objectMappers가 추가적으로 필요하다

    ```java
    // Response to Object
    HttpResponse<Book> book = Unirest.get("http://httpbin.org/books/1")
                       .asObject(Book.class);
    ```

- asObjectnew Object()) ...

파일형 결과

- 파일로 다시 바꾼다

    ```java
    File result = Unirest.get("http://some.file.location/file.zip")
                    .asFile("/disk/location/file.zip")
                    .getBody();
    ```

결과가 큰 경우

### Async

- 1초만에 응답하는 delay를 사용해 시험해본다

    ```java
    Future<HttpResponse<JsonNode>> future =
    Unirest.post("http://httpbin.org/delay/{time}")
    		.routeParam("time", "1")
    		.header("accept", "application/json")
        .asJsonAsync(new Callback<JsonNode>() {
    				@Override
    				public void completed(HttpResponse<JsonNode> response) {
    					int code = response.getStatus();
    					Headers headers = response.getHeaders();
    					JsonNode body = response.getBody();
    					System.out.println("code : " + code + "\nheader : " + headers + "\nbody : " + body);
    				}
    		});
    ```

    Callback 클래스는 추가적으로 failed와 canceled를 제공한다

    ```java
    @Override
    public void failed(UnirestException e) {
    	e.printStackTrace();
    }

    @Override
    public void cancelled() {
    	System.out.println("canceled");
    }
    ```

### Config

현재 unirestInstance의 설정을 변경한다

- defaultBaseUrl(String value)

    기본 url을 설정한다

    ```java
    Unirest.config().defaultBaseUrl("http://homestar.com");
        
       Unirest.get("/runner").asString();
    ```

### 종료

Unirest는 자동으로 꺼지지 않는다

- shutdown()

    Unirest를 종료한다

    만약, UnirestInstance를 추가로 생성했다면 그것 또한 종료해주어야 한다

    자동종료를 위해선, try문을 써야한다. 굳이?

    ```java
    try(UnirestInstance instance = Unirest.primaryInstance()){
        HttpResponse<String> response = instance.get("http://httpbin.org/get").asString();
        System.out.println(response.getBody());
    }catch (Exception e){}
    ```

참고:

http://kong.github.io/unirest-java/#responses

https://www.slideshare.net/rahulpatel184/unirest-java-tutorial-java-http-client