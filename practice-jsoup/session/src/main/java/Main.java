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


        System.out.println("meta:description:\n" + description);
        System.out.println("meta:keywords:\n"+ keywords);
    }
}
