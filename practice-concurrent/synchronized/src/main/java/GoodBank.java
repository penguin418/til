import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoodBank extends Bank {
    private static final Logger logger = LoggerFactory.getLogger(GoodBank.class);

    /**
     * 순서가 있는 동작
     */
    public void use() {
        enter();
        synchronized (this) {
            send();
            payCharge();
        }
        leave();
    }

    public void enter() {
        logger.info("들어온다");
    }

    public synchronized void send() {
        logger.info("송금한다");
    }

    public void payCharge() {
        logger.info("수수료를 낸다");
    }

    public void leave() {
        logger.info("나간다");
    }
}
