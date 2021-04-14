import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Queue;

public class SimplePipeline {
    private static final Logger logger = LoggerFactory.getLogger(SimplePipeline.class);
    private static final Object lock = new Object();
    private static final Queue<Integer> queue = new ArrayDeque<>();

    public void publishData(Integer value) throws InterruptedException {
        synchronized (lock) {
            logger.info("바꾸는 중");
            queue.add(value);
            lock.notifyAll();
        }
    }

    public String subscribeData() throws InterruptedException {
        String result = null;
        synchronized (lock) {
            // 비어있는 경우 대기
            if (queue.isEmpty()) {
                lock.wait();
            }
            StringBuilder sb = new StringBuilder();
            while(!queue.isEmpty()){
                sb.append(queue.poll());
            }
            result = sb.toString();
        }
        return result;
    }
}
