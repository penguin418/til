import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Receiver extends Thread {
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private SimplePipeline simpleStream;

    public Receiver(SimplePipeline simpleStream) {
        this.simpleStream = simpleStream;
    }

    @Override
    public void run() {
        while (true) {
            String data = null;
            try {
                data = simpleStream.subscribeData();
                logger.info(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
