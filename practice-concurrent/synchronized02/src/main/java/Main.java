import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) throws InterruptedException {
        SimplePipeline simpleStream = new SimplePipeline();
        Sender sender = new Sender(simpleStream);
        Receiver receiver = new Receiver(simpleStream);
        receiver.setDaemon(true);
        sender.start();
        receiver.start();
        sender.join();
        logger.info("샌더 종료");
        logger.info("리시버 종료");
    }

}
