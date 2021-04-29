import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        URL youtubeUrl = new URL("https://www.youtube.com");
        String fileName = DownloadUtil.absolutePath("download.html");

        if(DownloadUtil.download(youtubeUrl, fileName)){
            Document doc = Jsoup.parse(new File(fileName), "utf-8");
            String title = doc.title();
            String body = doc.body().text();
            System.out.println("Title:\n" + title);
            System.out.println("Body:\n" + body);
        }
    }
}
