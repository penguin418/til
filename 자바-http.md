# HTTP/1.1 ([RFC 7231](https://datatracker.ietf.org/doc/html/rfc7231))

## 정의

html 문서 교환용 통신규약으로 request, response message 모두를 포함한다. 

## Message 의 구조([RFC7230](https://datatracker.ietf.org/doc/html/rfc7230))

1. Start Line
    
    Start Line은 Request에서 request-line, Response 에서 status-line을 의미한다. 
    
    ### request-line
    
    ### status-line
    
    나중에 정리함
    
2. Header Fields
    
    ```jsx
    필드이름 : 필드값
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
        
        ```sql
        Basic <토큰>
        ```
        
        또는
        
        ```sql
        Bearer <토큰>
        ```
        
        이 헤더는 인증 토큰을 전달하는 헤더이다.
        
        토큰의 종류는 Basic과 Baerer가 있다.
        
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