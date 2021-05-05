import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Queue<Document> bq = new LinkedBlockingDeque<>();
        Queue<String> urls = new LinkedBlockingDeque<>();
        urls.add("https://google.com");
        List<Generator> generatorList = Stream.of(new Generator(bq, urls),new Generator(bq, urls),new Generator(bq, urls),new Generator(bq, urls))
                .collect(Collectors.toList());
        List<Consumer> consumerList = Stream.of(new Consumer(bq))
                .collect(Collectors.toList());
        ExecutorService es1 = Executors.newFixedThreadPool(4);
        generatorList.forEach(es1::submit);
        ExecutorService es2 = Executors.newFixedThreadPool(1);
        consumerList.forEach(es2::submit);
    }
}
