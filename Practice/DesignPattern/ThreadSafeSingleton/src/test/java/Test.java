import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test {
    private static final int TOTAL_THREADS = 3;
    private static final int TOTAL_EXECUTIONS = 5;

    public static void main(String[] args){
        Thread[] threads = new Thread[TOTAL_THREADS];
        for(int i=0; i<TOTAL_THREADS; i++){
            threads[i] = new Thread(() -> {
                ThreadSafeSingleton instance = ThreadSafeSingleton.getInstance();
                for(int j=0; j<TOTAL_EXECUTIONS; j++) {
                    instance.increaseCounter();
                }
            });
            threads[i].run();
        }

        ThreadSafeSingleton instance = ThreadSafeSingleton.getInstance();
        assertEquals(instance.getCounter(), TOTAL_THREADS * TOTAL_EXECUTIONS);
    }
}
