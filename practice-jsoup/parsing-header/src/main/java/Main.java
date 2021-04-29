import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
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
}
