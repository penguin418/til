package penguin.taskexecutor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlService {
    GenericObjectPool<GoogleSearchResultCrawlTask> taskPool = new GenericObjectPool<>(new GSRCTaskPool());

    @Async
    Future<List<String>> crawlGSR(int page_id) throws Exception {
        log.info(page_id + " 시작됨. " + page_id + "개의 컨텐츠가 있다고 가정");
        List<String> result = new ArrayList<>();
        Map<GoogleSearchResultCrawlTask, Future<String>> taskResults = new ConcurrentHashMap<>();

        for (int i = 0; i < page_id; i++) {
            GoogleSearchResultCrawlTask gsrcTask1 = taskPool.borrowObject();
            Future<String> future = gsrcTask1.crawl(page_id, i);
            taskResults.put(gsrcTask1, future);
        }
        for (Map.Entry<GoogleSearchResultCrawlTask,Future<String>> entry : taskResults.entrySet()) {
            GoogleSearchResultCrawlTask gsrcTask2 = entry.getKey();
            Future<String> future = entry.getValue();
            result.add(future.get());
            taskPool.returnObject(gsrcTask2);
        }
        log.info(page_id +" 종료됨. ");
        return new AsyncResult<>(result);
    }
}