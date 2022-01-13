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
    
    - Cache-Control
    - Expect
    - Host
    - Max-Forwards
    - Pragma
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
    
    - Accept
    - Accept-Charset
    - Accept-Encoding
    - Accept-Language
    
    ### Authentication Credentials
    
    인증 자격을 전달하는 헤더로 다음의 두 종류가 있다
    
    - Authorization
    - Proxy-Authorization
    
    ### Request Context
    
    요청 컨텍스트를 전달하는 헤더로 다음의 종류가 있다
    
    - From
    - Referer
    - User-Agent
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