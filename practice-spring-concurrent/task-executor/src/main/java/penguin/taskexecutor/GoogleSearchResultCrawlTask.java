package penguin.taskexecutor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;

@Slf4j
public class GoogleSearchResultCrawlTask  {
    public AsyncResult<String> crawl(int pageId, int contentId) throws InterruptedException {
        log.info(pageId + "수행함. " + contentId + "번 컨텐츠 발견");
        Thread.sleep(100);
        return new AsyncResult<String>("done");
    }
}
