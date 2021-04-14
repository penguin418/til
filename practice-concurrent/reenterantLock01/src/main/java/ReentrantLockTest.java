import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockTest.class);
    private static final ReentrantLock lock = new ReentrantLock();
    private static int cnt = 0;

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            test();
        }
    }

    private void test() {
        try {
            // [필수] try 밖에서 lock 획득
            // tryLock 을 사용해서 대기 가능
            while (!lock.tryLock(0, TimeUnit.MILLISECONDS)) {
                logger.info("대기");
            }
            try {
                cnt += 1;
                logger.info(String.valueOf(cnt));
            } finally {
                // [필수] finally 안에 unlock
                lock.unlock();
            }
        // tryLock 사용 시 2중 try-catch 로 인터럽트 보호 필요
        } catch (InterruptedException ignored) {
            logger.warn("인터럽트");
        }
    }
}
