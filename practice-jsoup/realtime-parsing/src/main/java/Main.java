import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        String youtubeUrl = "https://www.youtube.com";

        Document doc = Jsoup.connect(youtubeUrl).get();
        String title = doc.title();
        String body = doc.body().text();

        System.out.println("Title:" + title);
        System.out.println("Body:" + body);
    }
}
