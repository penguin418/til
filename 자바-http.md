# java & http

# HTTP/1.1 ([RFC 7231](https://datatracker.ietf.org/doc/html/rfc7231))

## 정의

html 문서 교환용 통신규약으로 request, response message 모두를 포함한다. 

## Message 의 구조([RFC7230](https://datatracker.ietf.org/doc/html/rfc7230))

1. Start Line
    
    Start Line은 Request에서 request-line, Response 에서 status-line을 의미한다. 
    
    - request에서 메시지 구조
        1. request-line
        2. header fields
        3. 띄어쓰기(CRLF)
        4. message
    - response에서 메시지 구조
        1. status-line
        2. header fields
        3. 띄어쓰기(CRLF)
        4. message
    
    ### request-line
    
    ### response-line
    
    ```sql
    <HTTP-Version> <Status-Code> <Reason-Phrase>
    ```
    
    ```sql
    HTTP/1.1 200 OK
    ```
    
    - HTTP-Version
        
        http 버전을 나타낸다
        
        요즘은 주로 1.0, 1.1과 2로 나뉜다
        
    - Status-Code
        
        3자리 숫자로, 확장가능하다. HTTP 어플리케이션은 모든 코드를 구현할 필요는 없지만 XYZ 형식의 Status-Code는 X00 Status-Code에 준하게 취급해야 한다.
        
        예를 들어 서버가 499라는 코드를 반환했을 때 클라이언트는 해당 코드를 몰라도 400번대에 해당하는 요청 오류의 한 종류로 취급하여야 한다.
        
    - Reason-Phrase
        
        사람을 위한 상태 코드 설명란으로 클라이언트는 해당 항목을 검사할 의무가 없다
        
    - Status-Code와 Reason-Phrase 추천 목록
        
        해당 추천목록은 5개의 클래스로 나눠져 있다. 클래스 내의 각 항목은 프로토콜에 영향을 주지 않고 수정될 수 있다. 
        
        - 1xx: 정보 유형
            
            요청이 접수 / 진행중 등
            
            100 - Continue
            
            101 - Switch Protocols
            
        - 2xx: 성공 유형
            
            액션이 성공함, 받아들여짐 등
            
            200 - OK
            
            201 - Created
            
            202 - Accepted
            
            203 - Non-Authroitative Information
            
            204 - No Content
            
            205 - Reset Content
            
            206 - Partial Content
            
        - 3xx: 리다이렉션 유형
            
            현재 요청을 처리하기 위해 추가적인 액션이 요구됨
            
            300 - Multiple Choices
            
            301 - Moved Permanently
            
            302 - Found
            
            302 - See Other
            
            304 - Not Modified
            
            305 - Use Proxy
            
            307 - Temporary Redirect
            
        - 4xx: 클라인언트 에러 유형
            
            요청이 아예 잘못되었거나 처리할 수 없는 요청임
            
            400 - Bad Request
            
            401 - Unauthoirzed
            
            402 - Payment Required
            
            403 - Forbidden
            
            404 - Not Found
            
            405 - Method Not Allowed
            
            406 - Not Acceptable
            
            407 - Proxy Authentication Required
            
            408 - Request Time0out
            
            409 - Conflict
            
            410 - Gone
            
            411 - Length Required
            
            412 - Precondition Failed
            
            413 - Request Entity Too Large
            
            414 - Request-URI Too Large
            
            415 - Unsupported Media Type
            
            416 - Requested range not satisfiable
            
            417 - Expectation Failed
            
        - 5xx: 서버 에러 유형
            
            요청은 유효하나, 서버가 요청을 처리하지 못함
            
            500 - Internal Server Error
            
            501 - Not Implemented
            
            502 - Bad Gateway
            
            503 - Service Unavailable
            
            504 - Gateway Time-out
            
            505 - HTTP Version not supported
            
2. Header Fields
    
    ```jsx
    <Field-Name> : <Field-Value>
    ```
    
    필드는 확장될 수 있으나, 기본적인 헤더가 존재한다
    
    ### Control Data
    
    처리를 지시하는 헤더로 다음의 종류가 있다
    
    - Cache-Control (HTTP/1.1)
    - Expect (HTTP/1.1)
        
        ```jsx
        100-continue
        ```
        
        이 헤더는 클라이언트가 특정 동작을 수행 전에, 서버의 수용 가능 여부를 묻기 위해 동작한다. 내용은 `100-continue`만 허용(대소문자 지킬 것)된다.
        
        클라이언트가 body을 보내기 전, Content-Type, Content-Length, Expect 헤더를(Option이 아니다.) 먼저 보낸 다음, 서버에서 `100-continue`라고 응답하면, data를 마저 보낸다. (보내지 않은 경우에도 마저 보낸다.) 하지만 않은 경우엔 서버가 이외의 코드(400번대)로 응답하면 클라이언트에서 data는 전송하지 않는다. 
        
        예를 들어 서버가 100메가 파일까지만 업로드를 허용하는 경우, 요청의 Content-Length를 확인한 다음, `400  (Bad Request)` 라고 응답하면 클라이언트는 data를 보내지 않는다.
        
        주의: 몇몇 클라이언트(예: [curl 에서 put,post요청](https://gms.tf/when-curl-sends-100-continue.html) 등)는 자동으로 보내므로 유의해야 한다. 해당 요청을 받은 서버 (혹은 게이트웨이)가 HTTP/1.1 이전이라면 즉시 `417 (Expectation Failed)`를 반환하므로,  요청이 거부될 수 있다. 해결 방법은 해당 헤더 값을 null로 덮어씌우거나 사용하지 않는 것이다.
        
    - Host
        
        ```sql
        example.com:4333
        ```
        
        이 헤더는 서버의 도메인 네임을 나타낸다. 필수 값이며 포트를 포함한다.
        
    - Max-Forwards
        
        ```jsx
        <숫자>
        ```
        
        이 헤더는 TRACE 메서드에 의해서만 제한적으로 사용된다. 이 값은 각각의 홉을 지나기 직전에 1을 감소시킨다. 0인 경우, 출발지점으로 되돌려준다.
        
        - 0으로 출발하면 아예 출발도 하지 못한다는 의미이다. (기본값은 70이다)
        
        TTL과 같은 의미이다. HTTP, SMTP 프로토콜을 일부 차용하여 만들어진 SIP 프로토콜에서는 간단한 이슈 원인으로 쓰이는 것 같다.
        
    - Pragma (HTTP/1.0)
        
        ```jsx
        no-cache
        ```
        
        이 헤더는 캐시동작을 제어하기 위한 헤더이다. HTTP/1.1의`Cache-Control: no-cache`와 동일한 의미로, HTTP/1.0 클라이언트와의 하위호환성을 위해서 사용되지만, 구현이 표준화되어 있지 않으므로 실제 동작은 서버에 따라 다르게 동작할 수 있다.
        
    - Range
        
        
    - TE
    
    ### Conditionals
    
    실행 조건을 지시하는 헤더로 다음의 종류가 있다
    
    - If-Match
    - If-None-Match
    - If-Modified-Since
    - If-Unmodified-Since
    - If-Range
    
    ### Content Negotiation
    
    컨텐츠를 협상하는 헤더로 다음의 종류가 있다
    
    공통적으로 세미콜론(;) 뒤에 q=<품질>을 추가하여 선호하는 품질을 명시할 수 있다.
    
    - Accept
        
        ```sql
        <주타입>/<보조타입> [; q=품질]
        ```
        
        이때, 품질은 0~1사이로 1인경우 생략된다.
        
        이 헤더는 서버에게 요청하는 콘텐츠 타입을 의미한다. 코마(,)를 사용하여 여러 개 사용할 수 있다.
        
        - **/**
            
            어떤 것이든 상관없다는 의미이다.
            
        - text/html
        - image/png
        - text/*
        - application/json
    - Accept-Charset
        
        ```sql
        utf-8; q-0.9
        ```
        
        사용하지 않는 경우 ISO-8859-1
        
    - Accept-Encoding
        
        이 헤더는 클라이언트가 지원하는 콘텐츠 압축방식을 명시한다.
        
        - gzip
        - deflate
    - Accept-Language
        
        ```sql
        ko-KR,ko;q-0.9
        ```
        
        이 헤더는 클라이언트가 지원하는 언어를 명시한다.
        
    
    ### Authentication Credentials
    
    인증 자격을 전달하는 헤더로 다음의 두 종류가 있다
    
    - Authorization
        
        이 헤더는 인증 토큰을 전달하는 헤더이다. `Authentication`이 아니다.
        
        ```bash
        Authorization: <type> <credentials>
        ```
        
        type은 인증방식으로 종류는 Basic과 Baerer가 있다.
        
        - Basic
            
            ```bash
            Base64(<user>:<password>)
            ```
            
            basic타입은 사용자의 아이디와 비밀번호를 :(콜론)으로 이어서 Base64 인코딩한 값을 사용한다. 보안을 위해 https 만 사용할 것을 권장한다
            
        - Bearer
            
            bearer 타입은 서버가 정의한 규칙을 사용한다
            
        
        credentials은 인증정보이다.
        
    - Proxy-Authorization
    
    ### Request Context
    
    요청 컨텍스트를 전달하는 헤더로 다음의 종류가 있다
    
    - From
    - Referer
    - User-Agent
        
        요청을 보낸 클라이언트
        
    
    ### 그 외 유명한 헤더
    
    - Date
        
        HTTP 메시지가 만들어진 시간(자동생성)
        
    - Content-Length
        
        본문의 크기 (바이트)
        
    - Content-Type
        
        이 헤더는 body의 데이터 타입을 나타내는 헤더이다. body가 있는 메서드(POST, PUT, DELETE)에서만 의미가 있다.
        
        - text/html : html
        - www-url-form-encoded : 폼
        - multipart/form-data
    - Content-Encoding
        
        콘텐츠의 압축 방식
        
        - gzip
        - deflate
    
    ### Response 헤더
    
    리스폰스 헤더는 status-line에서 제공하지 못하는 추가적인 정보를 전달하기 위해 사용한다
    
    → 그러므로 서버에서 클라이언트에 보내는 응답에 대한 설명을 여기다 추가해도 괜찮을 것 같다.
    
    - Age
        
        ```sql
        Age : <Age-Value>
        ```
        
        이 헤더는 객체가 프록시 서버에 머문 시간으로 캐시 신선도를 표현한다.
        
    - ETag
        
        ```sql
        ETag : <Entity-Tag>
        ```
        
        이 헤더는 entity의 태그의 현재값을 나타내며, 같은 리소스임을 확인하기 위해 사용된다
        
    - Location
        
        ```sql
        Location : <absolute url>
        ```
        
        이 헤더는 리다이렉션 시 이동할 url을 지정하기 위해 사용된다.
        
        Status-Code가 다음일 때,
        
        - 201: 새로 생성된 객체의 url을 지정한다
        - 3XX: 리다이렉션될 url을 지정한다
    - Proxy-Authenticate
    - Retry-After
    - Server
        
        ```sql
        Server : 1*( product | comment )
        ```
        
        이 헤더는 origin 서버에서 사용되는 서버의 정보를 표기하기 위해 사용된다. 프록시 서버는 origin 서버의 정보를 훼손하지 않아야 하므로, 서버에서 설정한 값이 그대로 전달되게 된다. 
        
        요즘은 Server 정보를 숨기는 것이 주요 보안사항으로, SpringBoot 에서는 다음처럼 [application.properties](http://application.properties) 를 수정하여 숨길 수 있다
        
        ```sql
        server.server-header=server
        ```
        
    - Vary
        
        ```sql
        Vary : ( "*" | 1#field-name )
        ```
        
        이 헤더는 캐시된 페이지를 사용해도 되는지 판단하기 전에 확인해야할 요청 헤더 목록을 나타낸다. 
        
        예를 들어 Accept-Language헤더에 따라 다른 값을 제공한다면Accept-Language를 적을 수 있다. 이 경우, 프록시 서버는 요청 헤더의 Accept-Language가 저장된 페이지의 Accept-Language와 다른 경우, origin 서버에 해당 페이지를 다시 요청하게 된다.
        
        *를 쓰게 되면, 프록시 서버들은 Accept-Language, Accept-Encoding, User-Agent, Referer 등 모든 조합이 모두 일치하는 페이지를 가질 때만 해당 페이지를 재사용하게 되므로, 대부분의 경우 모든 요청을 받으면 origin 서버에서 원본을 찾아 돌려주게 될 것이다. 따라서 *은 안쓰는 것이 좋다.
        
    - WWW-Authenticate
        
        ```bash
        WWW-Authenticate : 1#challenge
        ```
        
        이 헤더는 인증 없는 클라이언트의 인증이 필요한 리소스 요청에 대한 응답으로, Status-Code가 401이어야 한다.
        
        challenge는 1개 이상 입력될 수 있고 각 challenge는 ,(쉼표)로 구분된다.
        
        - challenge
            
            ```bash
            <auth-scheme> [옵션]
            ```
            
            - auth-scheme
                
                인증 방법이다. 종류로는 basic, digest, hoba, mutual, negotiate 등이 있다. 각 종류에 따라 필요한 옵션이 다르다.
                
                예를 들어, Basic의 경우는 다음의 옵션을 사용가능하다
                
                ```bash
                WWW-Authenticate : Basic realm=account.example.com charset="UTF-8"
                ```
                
                여기서 realm은 보호된 영역으로, 적혀있지 않다면 브라우저는 hostname(example.com이 될 것이다)으로 유추한다. realm에 적힌 영역에 대해선 같은 인증을 사용할 수 있다. charset은 그냥 charset이다.
                
        
        → 그런데, 클라이언트에서 사용하라고 만든 헤더치고는 웹페이지에서 WWW-Authenticate 헤더를 사용하는 곳을 거의 못본 것 같다. google, facebook, amazon, naver, kakao 모두, 401대신 404페이지를 출력하거나, 302를 사용해 로그인 페이지로 이동시켰다. 
        
        → WWW-Authenticate 헤더는 웹페이지보다는 REST-API쪽에서만 사용되는 것 같다.
        
        출처: 
        
        - [https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/WWW-Authenticate](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/WWW-Authenticate)
        - [https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication#authentication_schemes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication#authentication_schemes)
3. Message Body

# 클라이언트의 IP식별

로그인 시 유저 아이피 기록 등, 여러 이유로 접속자의 아이피가 필요한 경우가 있다. 이때, getRemoteAddr만으로는 프록시나 로드밸런서 뒤의 주소를 알 수 없으므로 헤더정보를 사용한다

- X-Forwarded-For
    
    ```jsx
    X-Forwarded-For: 클라이언트IP, 프록시IP, 프록시2IP...
    ```
    
    사실상 표준(De-Facto)으로 rfc로 지정된 forwarded보다 많이 쓰인다.
    
    가장 왼쪽 아이피가 클라이언트의 아이피이다.
    
- 그외
    
    표준이 아니다보니, 안지키는 곳도 많다. WL-Proxy-Client-IP ,Proxy-Client-IP도 같은 기능을 제공하니 모두 확인하면 좋을 것 같다.