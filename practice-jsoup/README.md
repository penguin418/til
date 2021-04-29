자바-프레임워크-Jsoup

# Basic parsing

간단한 html 문자열을 파싱할 수 있습니다

```java
// Main의 main 메서드
String htmlString
        = "<html>" +
        "<head>" +
        "<title>title</title>" +
        "</head>" +
        "<body>body</body>" +
        "</html>";
Document doc = Jsoup.parse(htmlString);
String title = doc.title();
String body = doc.body().text();

System.out.println("Title:" + title);
System.out.println("Body:" + body);

```

# Local Parsing

로컬 html 파일을 읽고 파싱할 수 있습니다

```java
// Main의 main 메서드
URL youtubeUrl = new URL("https://www.youtube.com");
String fileName = DownloadUtil.absolutePath("download.html");

if(DownloadUtil.download(youtubeUrl, fileName)){
    Document doc = Jsoup.parse(new File(fileName), "utf-8");
    String title = doc.title();
    String body = doc.body().text();
    System.out.println("Title:\n" + title);
    System.out.println("Body:\n" + body);
}
```

- 참고

    ```java
    // DownloadUtil의 download 메서드
    public static boolean download(URL url, String filePath) {
            try ( // auto-closable
                  ReadableByteChannel urlReadChannel = Channels.newChannel(url.openStream());
                  FileOutputStream fileOutStream = new FileOutputStream(filePath);
                  FileChannel fileChannel = fileOutStream.getChannel();
            ) { // try
                return fileChannel.transferFrom(urlReadChannel, 0, Long.MAX_VALUE) > 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    ```

# Realtime-Parsing

웹에서 파싱할 수 있습니다

```java
// Main의 main 메서드
String youtubeUrl = "https://www.youtube.com";

Document doc = Jsoup.connect(youtubeUrl).get();
String title = doc.title();
String body = doc.body().text();

System.out.println("Title:" + title);
System.out.println("Body:" + body);
```

# Session

파싱 외에 요청 값을 조작하여 세션을 따라할 수 있습니다

```java
// Main의 main 메서드
String youtubeUrl = "https://www.youtube.com";

// get 을 바로 하는 대신 header 를 추가하고 get 할 수도 있다
// Jsoup.connect(youtubeUrl).get();
Connection connection = Jsoup.connect(youtubeUrl);

// execute 의 결과는 Response 이다
// connection 에 header 를 담고 바로 execute 하는 대신, .get() 을 해도 된다
Connection.Response response =
        // 한국어 1순위, ko 2순위, en을 3순위로 요청한다
        connection.header("Accept-Language", "kr-KO, ko;q=0.9, en;q=0.8")
                .timeout(1000)
                .method(Connection.Method.GET)
                .execute();

// response 에는 header 가 들어있다
for(Map.Entry<String, String> header : response.headers().entrySet()){
    System.out.printf("%s:\n        %s\n", header.getKey(), header.getValue());
}
// response 는 connection.response() 를 통해서도 얻을 수 있다
assert response == connection.response();

// 이때, document 는 다음의 방법으로 구한다
Document doc = response.parse();

// html 은
// meta data
String description = doc.select("meta[name=description]").first().attr("content");
String keywords = doc.select("meta[name=keywords]").first().attr("content");
```

# Parsing Header

헤더를 파싱할 수 있습니다

```java
// Main의 main 메서드
String youtubeUrl = "https://www.youtube.com";

// connect 만 하면 Document 가 아니라 Connection 객체를 반환한다
// Document doc = Jsoup.connect(youtubeUrl).get();
Connection connection = Jsoup.connect(youtubeUrl);

// Connection 객체를 사용하면 header 나 다른 request 를 추가할 수 있다
// 예를 들면 Accept-Language 를 사용하여 선호하는 언어를 명시한다
// ( 한국어 1순위, ko 2순위, en을 3순위로 요청 )
connection.header("Accept-Language", "kr-KO, ko;q=0.9, en;q=0.8");

// 예를 들면 Accept-Encoding 을 사용하여 압축을 지정할 수 있다
// (압축 타입으로 gzip 을 요청)
connection.request().header("Accept-Encoding", "gzip");

// execute 의 결과는 Response 이다
// connection 에 header 를 담고 바로 execute 하는 대신, .get() 을 해도 된다
Connection.Response response =
                connection.timeout(3000)
                .method(Connection.Method.GET)
                .execute();
// 반환값을 받지 않았더라도 connection.response() 를 통해서도 response 를 얻을 수 있다
// 코드가 너무 길어진다면 이렇게 하면 된다
assert response == connection.response();

// response 를 사용할 때는 document 를 다음의 방법으로 구한다
Document doc = response.parse();

// response 에는 header 가 들어있다
for(Map.Entry<String, String> header : response.headers().entrySet()){
    System.out.printf("%s:\n        %s\n", header.getKey(), header.getValue());
}

// response 에는 header 가 있는 만큼, 쿠키도 들어있다
Map<String, String> cookies = response.cookies();
// 만약 스프링 기반의 기본 세션이라면 다음 키에 세션아이디가 들어있을 것이다
String sessionId = cookies.get("JSESSIONID");
System.out.println("session id: " + sessionId);

// jsoup 은 세션이 없으므로, 쿠키를 사용하여 세션을 유지한다
// 로그인을 한 경우, 쿠키를 직접 넣어주어야 할 수도 있다
Jsoup.connect(youtubeUrl)
        // 전체 다 넣어줘도 된다
        .cookies(cookies)
        .method(Connection.Method.GET)
        // 단일 쿠키를 추가할 경우는 다음처럼 한다
        // .cookie(키, 값)
        .execute();
}
```

# Select Elem

요소들을 파싱할 수 있습니다

```java
// Main의 main 메서드
String youtubeUrl = "https://www.youtube.com";

Document doc = Jsoup.connect(youtubeUrl).get();

// html 파일의 구조
// <!DOCTYPE ... >
// <html> 전체 html 파일을 감싼다
String html = doc.html(); // 전체 html
// <!doctype html>
// <html style="font-size: 10px;font-family: Roboto, Arial, sans-serif;" lang="ko-KR">
//  <head> ...

// <head> 헤드
Element headElem = doc.head(); // 헤드 전체를 가진 element 를 반환한다

// ......<meta> 메타 정보
// meta 데이터는 meta 요소를 가지고 있다
// <meta name="description" content="~~">
String description = headElem.select("meta[name=description]").first().attr("content");
// YouTube에서 마음에 드는 동영상과 음악을 감상하고, 직접 만든 콘텐츠를 업로드하여 친구, 가족뿐 아니라 전 세계 사람들과 콘텐츠를 공유할 수 있습니다.
String keywords = headElem.select("meta[name=keywords]").first().attr("content");
// 동영상, 공유, 카메라폰, 동영상폰, 무료, 올리기

// ......<link> 보통은 css
// ......<script> 보통은 js
// 보통의 경우 css, js 는 사용할 이유가 없다. 삭제는 아래처럼 한다
doc.select("link,script").remove();

// ......<title> 제목
String title = doc.title();
// YouTube

// <body>
// body 전체를 가진 element 를 출력해 낼 수 있다
Element bodyElem = doc.body();

// ......<br> 띄어쓰기
// 띄어쓰기로 대체하려면 append 를 사용한다
// 이때, prettyPrint 설정을 종료해야 한다
doc.outputSettings().prettyPrint(false);
doc.select("br").prepend("\\n");
// ......<i>, <hr>, select // 안 다룸

// ......<p> 단락
// 단락 안의 띄어쓰기를 유지하려면 false 옵션을 사용해야 한다
doc.outputSettings().prettyPrint(false);

// ......<h1> 제목
// 요소를 고르기 위해선 plainText 로 요소를 적으면 된다
Elements h1Elems = doc.select("h1");
// 이때, 모든 요소가 선택되며 아래 방법으로 순환할 수 있다
for(Element h1Elem: h1Elems);
// 순환 할 필요 없으면 첫번째 것을 사용한다
Element h1Elem = h1Elems.first();

// ......<div> 상자
Elements divElems = doc.select("div");

// ......<span> 디자인
Elements spanElems = doc.select("span");

// ......<img> 이미지
Elements imgElems = doc.select("img");

// ......<input> 입력란
Elements inputElems = doc.select("input");

// ......<button> 버튼
Elements buttonElems = doc.select("button");

// ......<form> 폼
Elements formElems = doc.select("form");

// ......<a> 링크
Elements linkElems = doc.select("a");
```

# Select Attr

[ ] 를 사용하여 속성을 파싱할 수 있습니다

```java
// Main의 main 메서드
String youtubeUrl = "https://www.youtube.com";

Document doc = Jsoup.connect(youtubeUrl).get();

// 속성은 [] 안에서 검색할 수 있다

// 예를 들어, 유튜브에는 다음과 같은 링크가 아주 많다
// <a slot="guide-links-primary" ... >

// a 요소이고, slot 속성이 guide-links-primary 일 때,
Elements linkElems = doc.select("a[slot=guide-links-primary]");

String[] hrefs = linkElems.stream().map(elem->elem.attr("href")).toArray(String[]::new);
System.out.println(Arrays.toString(hrefs));
// [https://www.youtube.com/about/, https://www.youtube.com/about/press/, ...

// 정규식을 이용해서도 검색이 가능하다
Elements detailElems = doc.select("div[class^=details]");
System.out.println(detailElems);
// <div class="details"><div class="channel-ava

// jsoup 문서에 따르면 이외에도 아래의 옵션들이 있다
//    el[attr]: attr 속성을 가진 el 요소, e.g. a[href]
//    [attr]: attr 속성을 가진 요소, e.g. [href]
//    [^attr]: attr 로 시작하는 속성을 가진 요소, e.g. [^data-] finds elements with HTML5 dataset attributes
//    [attr=value]: attr 의 속성이 value 인 요소, e.g. [width=500] (also quotable, like [data-name='launch sequence'])
//    [attr^=value], attr 의 속성이 value 로 시작하는 요소
//    [attr$=value], attr 의 속성이 value 로 끝나는 요소
//    [attr*=value]: attr 의 속성이 value 를 포함하는 요소, e.g. [href*=/path/]
//    [attr~=regex]: attr 의 속성이 정규식 regexp 를 포함하는 요소 e.g. img[src~=(?i)\.(png|jpe?g)]
//    *: 모든 것, e.g. *
//    https://jsoup.org/cookbook/extracting-data/selector-syntax
```

# Select Selector

```java
// Main의 main 메서드
String youtubeUrl = "https://www.youtube.com";

Document doc = Jsoup.connect(youtubeUrl).get();
// 띄어쓰기를 사용하여 요소A 의 하위 요소B 를 찾을 수 있다

// 예를 들어 유튜브의 html 파일의 내용이 있다
// <div class="video-details">
// <div class="rich-thumbnail skeleton-bg-color"></div>
// <div class="details"> ...

// div 요소 중 video-detail 클래스 안의 div 들을 파싱하고 싶다면,
Elements elems = doc.select("div.video-details div");
// <div class="rich-thumbnail skeleton-bg-color"></div>
// <div class="details"> ...

// jsoup 문서에 따르면 이외에도 아래의 옵션들이 있다
//    el#id: id를 아이디로 하는 el 요소, e.g. div#logo
//    el.class: class 클래스를 가진 el 요소, e.g. div.masthead
//    el[attr]: attr 속성을 가진 el 요소, e.g. a[href]
//    ancestor child: ancester 요소 아래의 child 요소, e.g. .body p finds p elements anywhere under a block with class "body"
//    parent > child: parent 요소 바로아래 있는 child 요소, e.g. div.content > p finds p elements; and body > * finds the direct children of the body tag
//    siblingA + siblingB: siblingA 요소 바로 뒤에오는 siblingB 요소, e.g. div.head + div
//    siblingA ~ siblingX: siblingX 요소 바로 뒤에오는 siblingB 요소, e.g. h1 ~ p
//    el, el, el: 그룹 셀렉터, e.g. div.masthead, div.logo
//    https://jsoup.org/cookbook/extracting-data/selector-syntax
```