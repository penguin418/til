import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Generator implements Runnable {
    private final Queue<Document> queue;
    private final Queue<String> urls;

    public Generator(Queue<Document> queue, Queue<String> dequeue) {
        this.queue = queue;
        this.urls = dequeue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String url = this.urls.poll();
                System.out.println(url);
                Document document = Jsoup.connect(url).get();
                queue.offer(document);
                List<String> urls = getLinks(document);
                this.urls.addAll(urls);
            } catch (IllegalStateException e1) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getLinks(Document document) {
        List<String> urls = new ArrayList<>();
        Elements linksOnPage = document.select("a[href]");
        for (Element page : linksOnPage) {
            urls.add(page.attr("abs:href"));
        }
        return urls;
    }
}
