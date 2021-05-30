# POSTMAN 사용법

python 등을 사용해 스크립트로 쉽게 request를 보낼 수 있음에도 postman을 사용하는 이유는 request 기록을 훌륭하게 관리할 수 있기 때문이다

# 1. request 관리 방법

## 1.1. Collection

Collection은 관련있는 request들을 Folder로 관리하기 위한 그룹이다

모든 request들은 Collection에 속하며, 처음에 request를 만들기 위해서는 Collection을 만들어야 한다

### Collection의 기능

Collection은 하위의 request들이 공유하는 속성들을 관리할 수 있다.

- Environment

    미리 설정한 환경변수들을 Environment로 관리하여 다양한 접근권한을 테스트해볼 수 있다

- Authorization

    인증을 미리 설정할 수 있다. OAUTH1, OAUTH2 등 자주 쓰이는 인증방식은 여기서 간단하게 설정하여 인증을 수행할 수 있다

- Pre-request Script

    hmac 처럼 매번 메시지 인증 코드를 생성해야 하는 경우, 스크립트를 통해 인증값을 생성할 수 있다

- Tests

    간단하게 200 OK를 체크하는 스크립트를 여기에 추가하는 것만으로 하위의 모든 조회를 간단하게 테스트할 수 있다

    ```jsx
    pm.test("Status code is 200", function () {
        pm.response.to.have.status(200);
    });
    ```

- Variables

    지정한 Collection Variables 의 목록을 확인할 수 있다

    이유는 모르겠지만 여기서 추가하는 것보다 드래그해서 추가하는 것이 빠르게 적용되는 것 같다

    → persist 버튼을 눌러야 한다

## 1.2. request

http request를 작성할 수 있다. GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD를 포함하여 다양한 Http Method들을 지원한다

## 1.3. Folder

Collections 하위의 request들 중 관련있는 것들을 한데 모을 수 있는 그룹이다

# 2. 협업하기

## 2.1. Workspace

Workspace는 팀과 API프로젝트를 수행하는 것을 도와준다. Collection과 Environment를 공유할 수 있다

# 3. 변수

POSTMAN은 변수 생성을 지원한다. 원하는 값을 드래그하면 나오는  'Set as Variable' 메뉴를 선택하여 만들 수 있다

- Name: 변수이름
- Variable: 값
- Scope: 변수의 적용범위이다

변수를 만들면, request URL 혹은 header에서 사용할 수 있으며 다음 표기법을 사용하여 접근한다

```jsx
{{변수이름}}
```

시스템에서 미리 만들어둔 변수의 경우 다음 표기법을 사용한다(이외에도 많다)

```jsx
// 랜덤 단어
{{$randomWord}}
// 랜덤 단어 + 숫자 조합
{{$randomAlphaNumeric}}
// 현재 타임스탬프
{{$timestamp}}
```

POSTMAN에서 생성할 수 있는 변수의 스코프는 다음과 같다

- Global 변수

    pm.globals를 통해 관리하는 전역변수이다

    pm.globals.set 메서드를 사용해서 지정하거나, 값 드래그 후 설정할 수 있다

- Collection 변수

    Collection에서 공유하는 변수이다

    같은 이름의 Global 변수가 지정되있더라도 우선한다

    Collection 관리 페이지에서 추가하거나 값 드래그 후 설정할 수 있다

- Environment 변수

    지정된 Environment이 있는 경우 참조하는 변수이다

    같은 이름의 Global 변수나 Collection 변수가 지정되있더라도 우선한다

    Environment를 Collection이나, request에서 직접 선택해야 해서 그런 것 같다

    Environment 관리 페이지에서 추가할 수 있다

- Local 변수

    지정하면 현재 request에서만 사용할 수 있다

    pm.variables.set 메서드를 사용해서 지정할 수 있다

- Data 변수

    외부 파일(csv, json 등)에서 가져온 값을 사용한다

# 4. pre-request Script

pre-request Script는 각 request 전에 실행되는 자바스크립트이다.

POSTMAN은 미리 request 변수를 지정하여, pre-request Script에서 request 정보를 사용할 수 있게 해준다

```jsx
request // 요청에 관한 내용을 담고 있다
request['id'] // 고유 id
request['name'] // request의 이름
request['description'] // md 서식으로 작성한 설명
request['headers'] //  headers 정보
request['method'] // Http Method 정보
request['url'] // 요청 url
request['data'] // 요청 데이터
```

각 항목은 headers를 제외하고 모두 string으로 제공된다

만약, 관련 정보가 필요한 경우 파싱을 할 필요가 있다

아래 예시는 Naver 검색 광고에 사용되는 X-Signature를 생성하기 위한 스크립트이다.

다음 링크에서 Naver 검색 광고가 요구하는 스펙을 확인할 수 있다

- [https://naver.github.io/searchad-apidoc/#/samples](https://naver.github.io/searchad-apidoc/#/samples)

POSTMAN은 다양한 snippet을 제공하여, 현재 설정된 Environment에서 변수를 가져오거나, global variable등을 쉽게 가져올 수 있다

```jsx
// desktop 버전에서는 node.js의 주요 패키지를 사용할 수 있다
var URL = require('url')
// 미리 환경변수에 설정한 값들을 읽어올 수 있다
var API_SECRET = pm.environment.get('API_SECRET');

console.log(API_SECRET)
var timestamp = new Date().getTime();
var httpMethod = request['method'];
var requestUri = URL.parse(request['url']).pathname;

var message = [timestamp, httpMethod, requestUri].join(".");
var hash = CryptoJS.HmacSHA256(message, API_SECRET);
var hmacDigest = CryptoJS.enc.Base64.stringify(hash);

// Global 변수로 설정한 만큼, 헤더나, request url에서 {{xSignature}} 라고 써서 사용할 수 있다
pm.globals.set('xsignature', hmacDigest);
// 헤더 지정시 환경변수({{$timestamp}}를 통해 시간을 구할 수 있지만, 
// 그렇게 할 경우 여기서 계산할 때 사용한 시간과 다르게된다
pm.variables.set('xtimestamp', timestamp);
```

pre-requests Script는 Collection과 각 request 모두에 설정할 수 있다.

- 두 곳 모두에 스크립트가 있는 경우, Collection에 있는 스크립트가 먼저 수행되고 각 request에 있는 스크립트가 수행된다.
- 이때는  Collection의 스크립트에서 Local변수인 variables에 저장해서 넘겨줘도 request에서 받을 수 있다