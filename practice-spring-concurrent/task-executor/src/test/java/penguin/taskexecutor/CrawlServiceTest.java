package penguin.taskexecutor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CrawlServiceTest {
    @Autowired
    private CrawlService crawlService;

    @Test
    void 시험() throws Exception {
        List<Future<List<String>>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            futures.add(
                    crawlService.crawlGSR(i)
            );
        }

        for (int i = 0; i < 5; i++) {
            List<String> stringList = futures.get(i).get();
            System.out.println(stringList.toString());
        }
    }
}