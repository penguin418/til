import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

// abstract class
public abstract class AbstractNewsParser {
    private String title;
    private String body;

    public abstract String findTitle(Document doc);

    public abstract String findBody(Document doc);

    public final void parse(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            this.title = findTitle(doc);
            this.body = findBody(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return this.title + "\n\n" + this.body;
    }
}
