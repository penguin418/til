import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Main {
    public static void main(String[] args) {
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
    }
}
