package penguin.taskexecutor;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.springframework.stereotype.Component;

@Component
public class GSRCTaskPool extends BasePoolableObjectFactory<GoogleSearchResultCrawlTask> {
    @Override
    public GoogleSearchResultCrawlTask makeObject() throws Exception {
        return new GoogleSearchResultCrawlTask();
    }
}
