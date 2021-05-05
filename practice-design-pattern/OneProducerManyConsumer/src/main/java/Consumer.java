import org.jsoup.nodes.Document;

import java.util.Queue;

public class Consumer implements Runnable {
    private final Queue<Document> queue;

    public Consumer(Queue<Document> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            try {
                Document doc = this.queue.poll();
                if (doc != null) {
                    System.out.println(doc.title());
                }
            }catch (IllegalStateException ignored){
            }
        }
    }
}
