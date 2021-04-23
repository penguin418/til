import org.jsoup.nodes.Document;

public class NaverNewParser extends AbstractNewsParser {
    private String title;
    private String body;

    @Override
    public String findTitle(Document doc) {
        return doc.body()
                .select("#articleTitle")
                .text();
    }

    @Override
    public String findBody(Document doc) {
        return doc.body()
                .select("#articleBodyContents")
                .text();
    }
}
