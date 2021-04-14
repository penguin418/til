import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sender extends Thread {
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private SimplePipeline simpleStream;

    public Sender(SimplePipeline simpleStream) {
        this.simpleStream = simpleStream;
    }

    @Override
    public void run() {
        for(int i=0; i<1000; i++){
            try {
                simpleStream.publishData(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
