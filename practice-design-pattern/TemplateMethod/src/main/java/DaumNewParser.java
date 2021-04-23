import org.jsoup.nodes.Document;

public class DaumNewParser extends AbstractNewsParser {
    @Override
    public String findTitle(Document doc) {
        return doc.body()
                .select("#head_view").text();
    }

    @Override
    public String findBody(Document doc) {
        return doc.body()
                .select("#harmonyContainer").text();
    }
}
